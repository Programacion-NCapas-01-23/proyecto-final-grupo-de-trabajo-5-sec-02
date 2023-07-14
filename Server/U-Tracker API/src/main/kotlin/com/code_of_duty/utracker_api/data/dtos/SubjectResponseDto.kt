package com.code_of_duty.utracker_api.data.dtos

data class SubjectResponseDto(
    val code: String,
    val name: String,
    val assessments: List<AssessmentXStudentCycleDto>
)
