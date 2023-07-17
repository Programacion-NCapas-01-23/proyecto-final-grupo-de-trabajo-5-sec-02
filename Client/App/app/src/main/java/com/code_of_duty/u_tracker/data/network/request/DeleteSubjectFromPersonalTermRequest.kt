package com.code_of_duty.u_tracker.data.network.request

data class DeleteSubjectFromPersonalTermRequest(
    val studentCycleId: String,
    val subjectCode: String,
)
