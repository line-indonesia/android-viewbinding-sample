package com.linecorp.id.research.findviewbyid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.linecorp.id.research.common.data.PhaseStatusEnum
import com.linecorp.id.research.common.data.TransactionModel
import com.linecorp.id.research.common.extension.getStringFromResourceName
import com.linecorp.id.research.findviewbyid.R
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
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_transaction_history, parent, false)
        return ViewHolder(view) { position ->
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
            itemView: View,
            onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

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

            val textViewPhaseStatus = itemView.findViewById<TextView>(R.id.textViewPhaseStatus)
            textViewPhaseStatus.text = resources.getStringFromResourceName(transaction.phaseStatus.key)

            textViewPhaseStatus.setTextColor(
                    itemView.context.getColor(
                            getPhaseStatusStringColor(transaction.phaseStatus)
                    )
            )

            val textViewDetails = itemView.findViewById<TextView>(R.id.textViewDetails)
            textViewDetails.text = resources.getString(
                    R.string.transaction_history_detail,
                    transaction.id,
                    dateFormatter.format(transaction.createdAt)
            )

            val textViewAmount = itemView.findViewById<TextView>(R.id.textViewAmount)
            textViewAmount.text = resources.getString(
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
