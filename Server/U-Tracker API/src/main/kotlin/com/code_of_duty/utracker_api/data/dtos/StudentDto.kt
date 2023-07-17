package com.code_of_duty.utracker_api.data.dtos

import java.math.BigDecimal

data class StudentDto (
    val code: String,
    val name: String,
    val email: String,
    val image: String,
    val cum: BigDecimal,
    val degree: String,
    val faculty: String,
    val pensum: String,
    val approvedSubjects: Int
)