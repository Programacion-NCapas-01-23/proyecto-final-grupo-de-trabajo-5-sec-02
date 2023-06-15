package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty

data class PensumDto(
    val id: String? = null,
    @field:NotEmpty(message = "Name is required")
    val plan: String,
    @field:NotEmpty(message = "Degree is required")
    val degreeId: String,
)
