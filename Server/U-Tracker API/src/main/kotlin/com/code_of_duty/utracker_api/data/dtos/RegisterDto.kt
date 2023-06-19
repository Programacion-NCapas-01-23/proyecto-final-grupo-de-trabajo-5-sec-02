package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class RegisterDto(
    @field:NotEmpty(message = "Code cannot be empty")
    @field:Pattern(
        regexp = "^[0-9]{8}$",
        message = "Code must be an 8-digit number"
    )
    val code: String,
    @field:NotEmpty(message = "Username cannot be empty")
    val username: String,
    @field:Email(message = "Invalid email")
    val email: String,
    @field:NotEmpty(message = "Password cannot be empty")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=_*])(?=\\S+$).{8,}$",
        message = "Password must contain at least 8 characters, one uppercase, one lowercase, one number, and one special character"
    )
    val password: String,
    @field:NotEmpty(message = "Degree name cannot be empty")
    val degreeId: String
)

