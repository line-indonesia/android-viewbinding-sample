package com.linecorp.id.research.viewbinding.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linecorp.id.research.common.data.PhaseStatusEnum
import com.linecorp.id.research.common.data.TransactionModel
import com.linecorp.id.research.common.extension.getStringFromResourceName
import com.linecorp.id.research.viewbinding.R
import com.linecorp.id.research.viewbinding.databinding.ItemTransactionHistoryBinding
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class TransactionHistoryAdapter(
        private val listener: Listener
) : RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>() {

    private val dataList: MutableList<TransactionModel> = mutableListOf()

    private val numberFormatter: DecimalFormat
    private val dateFormatter: DateFormat

    init {
        val locale = Locale("in_ID")
        val symbols = DecimalFormatSymbols(locale)
        symbols.groupingSeparator = '.'

        numberFormatter = DecimalFormat("#,###,###", symbols)
        dateFormatter = SimpleDateFormat("dd MMMM yyyy HH:mm", locale)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ViewHolder(binding) { position ->
            listener.onItemClicked(dataList[position])
        }
    }

    fun clearItems() {
        dataList.clear()
    }

    fun addItems(list: List<TransactionModel>) {
        dataList.addAll(list)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = dataList[position]
        holder.bind(transaction, numberFormatter, dateFormatter)
    }

    class ViewHolder(
            private val binding: ItemTransactionHistoryBinding,
            onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(
                transaction: TransactionModel,
                numberFormatter: DecimalFormat,
                dateFormatter: DateFormat
        ) {
            val resources = itemView.resources

            binding.textViewPhaseStatus.text =
                    resources.getStringFromResourceName(transaction.phaseStatus.key)

            binding.textViewPhaseStatus.setTextColor(
                    itemView.context.getColor(
                            getPhaseStatusStringColor(transaction.phaseStatus)
                    )
            )

            binding.textViewDetails.text = resources.getString(
                    R.string.transaction_history_detail,
                    transaction.id,
                    dateFormatter.format(transaction.createdAt)
            )

            binding.textViewAmount.text = resources.getString(
                    R.string.transaction_history_amount,
                    numberFormatter.format(transaction.amount)
            )
        }
    }

    interface Listener {
        fun onItemClicked(transaction: TransactionModel)
    }

    companion object {
        fun getPhaseStatusStringColor(status: PhaseStatusEnum): Int =
                when (status) {
                    PhaseStatusEnum.APPROVED -> R.color.app_text_green_color
                    PhaseStatusEnum.IN_REVIEW -> R.color.app_text_black_color
                    PhaseStatusEnum.REJECTED -> R.color.app_text_red_color
                }
    }
}
