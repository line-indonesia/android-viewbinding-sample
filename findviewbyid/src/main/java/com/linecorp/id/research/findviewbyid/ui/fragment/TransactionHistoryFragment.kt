package com.linecorp.id.research.findviewbyid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linecorp.id.research.common.data.TransactionModel
import com.linecorp.id.research.findviewbyid.ui.adapter.TransactionHistoryAdapter
import com.linecorp.id.research.findviewbyid.R

class TransactionHistoryFragment : Fragment(), TransactionHistoryAdapter.Listener {

    private val transactionHistoryViewModel: TransactionHistoryViewModel = TransactionHistoryViewModel()
    private val transactionHistoryAdapter: TransactionHistoryAdapter = TransactionHistoryAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        R.layout.fragment_transaction_history,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView(view)
        initObservables()

        transactionHistoryViewModel.getTransactions()
    }

    private fun initRecyclerView(view: View) {
        val recyclerViewTransaction = view.findViewById<RecyclerView>(R.id.recyclerViewTransaction)
        recyclerViewTransaction.apply {
            adapter = transactionHistoryAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initObservables() {
        transactionHistoryViewModel.transactionLiveData()
            .observe(this) {
                handleTransaction(it)
            }
    }

    private fun handleTransaction(myTransactionHistory: List<TransactionModel>) {
        transactionHistoryAdapter.clearItems()
        transactionHistoryAdapter.addItems(myTransactionHistory)
        transactionHistoryAdapter.notifyDataSetChanged()
    }

    override fun onItemClicked(transaction: TransactionModel) {

    }
}
