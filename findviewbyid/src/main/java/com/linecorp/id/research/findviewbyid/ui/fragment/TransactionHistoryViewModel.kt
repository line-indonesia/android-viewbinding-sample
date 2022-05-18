package com.linecorp.id.research.findviewbyid.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linecorp.id.research.common.base.RxViewModel
import com.linecorp.id.research.common.data.PhaseStatusEnum
import com.linecorp.id.research.common.data.TransactionModel

class TransactionHistoryViewModel : RxViewModel() {

    private val _transactionLiveData: MutableLiveData<List<TransactionModel>> = MutableLiveData()
    fun transactionLiveData(): LiveData<List<TransactionModel>> = _transactionLiveData

    fun getTransactions() {
        val list = listOf(
            TransactionModel(
                1L, 3_000_000,
                PhaseStatusEnum.IN_REVIEW,
            ),
            TransactionModel(
                2L, 2_000_000,
                PhaseStatusEnum.APPROVED,
            ),
            TransactionModel(
                3L, 1_000_000,
                PhaseStatusEnum.REJECTED,
            ),
            TransactionModel(
                4L, 4_000_000,
                PhaseStatusEnum.APPROVED,
            ),
            TransactionModel(
                5L, 2_000_000,
                PhaseStatusEnum.IN_REVIEW
            )
        )
        _transactionLiveData.postValue(list)
    }
}
