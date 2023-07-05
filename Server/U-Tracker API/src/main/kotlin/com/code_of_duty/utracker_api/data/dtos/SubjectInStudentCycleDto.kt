package com.code_of_duty.utracker_api.data.dtos

import java.math.BigDecimal
import java.util.UUID


data class SubjectInStudentCycleDto (
    val studentCycleId: String,
    val subjectCode: String,
    val estimateGrade: BigDecimal?
)