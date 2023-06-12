package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

data class ClassTimeUpdateDto (
    @field:NotEmpty(message = "ClassTime id cannot be empty")
    val id: String,
    @field:NotNull(message = "ClassTime day cannot be empty")
    val day: Int,
    @field:NotEmpty(message = "ClassTime startHour cannot be empty")
    @field:Pattern(
        regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
        message = "ClassTime startHour must be in the format HH:MM"
    )
    val startHour: String,
    @field:NotNull(message = "ClassTime totalHours cannot be empty")
    val totalHours: Int
)