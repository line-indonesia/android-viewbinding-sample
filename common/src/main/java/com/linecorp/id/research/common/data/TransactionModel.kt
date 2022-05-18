package com.linecorp.id.research.common.data

interface LocalizedEnum {
    val key: String
}

enum class PhaseStatusEnum(
    override val key: String
) : LocalizedEnum {
    IN_REVIEW("enum_phase_status_in_review"),
    REJECTED("enum_phase_status_rejected"),
    APPROVED("enum_phase_status_approved"),
}

data class TransactionModel(
    val id: Long,
    val amount: Long,
    val phaseStatus: PhaseStatusEnum,
    val createdAt: Long = System.currentTimeMillis()
)
