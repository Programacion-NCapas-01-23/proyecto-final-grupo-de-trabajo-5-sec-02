package com.code_of_duty.utracker_api.data.dtos

import java.math.BigDecimal
import java.time.LocalDate


data class AssessmentResponseDto (
    val assessmentId: String = "",
    val name: String = "",
    val percentage: Int = 0,
    val date: LocalDate,
    val grade: BigDecimal?
)