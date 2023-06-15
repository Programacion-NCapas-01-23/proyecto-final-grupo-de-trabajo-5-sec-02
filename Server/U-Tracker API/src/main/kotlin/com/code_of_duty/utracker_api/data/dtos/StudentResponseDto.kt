package com.code_of_duty.utracker_api.data.dtos

import com.code_of_duty.utracker_api.data.models.Degree

data class StudentResponseDto(
    val code: String,
    val username: String,
    val image: String?,
    val cum: Float,
    val degree: Degree?
)
