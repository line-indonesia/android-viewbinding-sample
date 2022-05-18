package com.linecorp.id.research.viewbinding.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.linecorp.id.research.common.data.TransactionModel
import com.linecorp.id.research.viewbinding.databinding.FragmentTransactionHistoryBinding
import com.linecorp.id.research.viewbinding.ui.adapter.TransactionHistoryAdapter

class TransactionHistoryFragment : Fragment(), TransactionHistoryAdapter.Listener {

    private lateinit var binding: FragmentTransactionHistoryBinding
    private val transactionHistoryViewModel: TransactionHistoryViewModel = TransactionHistoryViewModel()
    private val transactionHistoryAdapter: TransactionHistoryAdapter = TransactionHistoryAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionHistoryBinding.inflate(
            LayoutInflater.from(context),
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initObservables()

        transactionHistoryViewModel.getTransactions()
    }

    private fun initRecyclerView() {
        binding.recyclerViewTransaction.apply {
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
