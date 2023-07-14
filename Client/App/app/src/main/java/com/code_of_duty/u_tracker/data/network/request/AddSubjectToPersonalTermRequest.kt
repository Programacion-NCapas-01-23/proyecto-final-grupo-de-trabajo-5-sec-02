package com.code_of_duty.u_tracker.data.network.request

data class AddSubjectToPersonalTermRequest(
    val studentCycleId: String,
    val subjectCode: String,
    val estimatedGrade: Int,
)
