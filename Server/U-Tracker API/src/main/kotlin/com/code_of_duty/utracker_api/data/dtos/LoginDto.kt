package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class LoginDto(
    @field:NotEmpty(message = "Code cannot be empty")
    @field:Pattern(
        regexp = "^[0-9]{8}$",
        message = "Code must be a 8 digit number"
    )
    val code: String,
    @field:NotEmpty(message = "Password cannot be empty")
    val password: String
)