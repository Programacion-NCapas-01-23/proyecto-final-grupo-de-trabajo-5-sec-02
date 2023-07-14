package com.code_of_duty.utracker_api.data.dtos

import java.math.BigDecimal
import java.util.UUID

data class AssessmentXStudentCycleDto (
        val id: UUID,
        val name: String,
        val percentage: Int,
        val grade: BigDecimal?,
        val subject: String
)