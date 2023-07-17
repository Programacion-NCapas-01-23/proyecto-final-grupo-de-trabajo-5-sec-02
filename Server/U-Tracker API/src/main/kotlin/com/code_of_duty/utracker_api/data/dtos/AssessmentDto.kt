package com.code_of_duty.utracker_api.data.dtos


import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

data class AssessmentDto (
    val subjectCode: String = "",
    val name: String = "",
    val percentage: Int = 0,
    val date: LocalDate,
    val grade: BigDecimal?
)