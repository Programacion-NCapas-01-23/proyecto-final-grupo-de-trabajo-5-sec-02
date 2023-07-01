package com.code_of_duty.utracker_api.data.dtos

data class StudentCycleResponseDto (
    val name: String,
    val cycleType: Int,
    val orderValue: Int?,
    val subjects: List<StudentSubjectDto>?
)