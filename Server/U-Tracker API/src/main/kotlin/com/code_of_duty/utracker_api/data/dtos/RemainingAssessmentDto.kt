package com.code_of_duty.utracker_api.data.dtos

import java.math.BigDecimal

data class RemainingAssessmentDto (
    val assessmentName: String,
    val expectedGrade: BigDecimal?
)