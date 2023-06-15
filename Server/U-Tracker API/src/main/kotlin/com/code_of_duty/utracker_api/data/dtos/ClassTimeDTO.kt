package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class ClassTimeDTO(
    @field:NotEmpty(message = "Day of the week is required")
    val day: Int,
    @field:NotEmpty(message = "Start time is required")
    @field: Pattern(
        regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
        message = "Start time must be in the format HH:MM"
    )
    val start: String,
    @field:NotEmpty(message = "End time is required")
    val end: Int
)
