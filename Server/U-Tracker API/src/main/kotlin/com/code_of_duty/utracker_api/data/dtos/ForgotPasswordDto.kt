package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class ForgotPasswordDto(
    @field:NotEmpty(message = "Email is required")
    @field:Email(message = "Email should be valid")
    val email : String,

    @field:NotEmpty(message = "Token is required")
    @field:Pattern(regexp = "^[a-zA-Z0-9]{6}\$", message = "Token should be alphanumeric with length 6")
    val token: String,

    @field:NotEmpty(message = "Password is required")
    val password : String,

    @field:NotEmpty(message = "Confirm password is required")
    val confirmPassword : String
)