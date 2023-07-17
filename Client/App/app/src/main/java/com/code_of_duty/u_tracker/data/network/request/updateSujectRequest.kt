package com.code_of_duty.u_tracker.data.network.request

import com.code_of_duty.u_tracker.enums.SubjectStatus

data class UpdateSubjectRequest(
    val studentCycleId: String,
    val subjectCode: String,
    val state: SubjectStatus,
    val grade: Float,
)
