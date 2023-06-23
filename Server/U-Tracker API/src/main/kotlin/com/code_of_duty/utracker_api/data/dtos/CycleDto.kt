package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty

data class CycleDto(
    val id: String?,
    val type: Int,
    @field:NotEmpty(message = "Name cannot be empty")
    val name: String,
    @field:NotEmpty(message = "Pensum id cannot be empty")
    val pensumId: String,
    val subjects: List<SubjectDto>? = null
)
