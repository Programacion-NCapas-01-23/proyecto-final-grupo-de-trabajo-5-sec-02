package com.code_of_duty.utracker_api.data.dtos

import java.math.BigDecimal

data class PassGradeDto (
    val actualGrade: BigDecimal,
    val gradeRemaining: BigDecimal,
    val message: String
)