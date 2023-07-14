package com.code_of_duty.utracker_api.data.dtos

import com.code_of_duty.utracker_api.data.enums.SubjectStatus
import java.math.BigDecimal
import java.util.*

data class UpdateSubjectStateDto(
    val studentCycleId: UUID,
    val subjectCode: String,
    val state: SubjectStatus,
    val grade: BigDecimal?
)