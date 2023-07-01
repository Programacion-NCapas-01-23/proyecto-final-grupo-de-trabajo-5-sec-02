package com.code_of_duty.utracker_api.data.dtos

import java.util.UUID

data class StudentCycleDto(
    val id: String?,
    val studentCode: String,
    val cycleType: Int,
    val year: Int,
    val subjects: List<String>?
)

