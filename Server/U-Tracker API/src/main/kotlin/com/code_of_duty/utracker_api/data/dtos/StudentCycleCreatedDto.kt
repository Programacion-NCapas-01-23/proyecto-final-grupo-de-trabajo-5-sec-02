package com.code_of_duty.utracker_api.data.dtos

data class StudentCycleCreatedDto(
    val studentCycleId: String,
    val cycleType: Int,
    val year: Int
)