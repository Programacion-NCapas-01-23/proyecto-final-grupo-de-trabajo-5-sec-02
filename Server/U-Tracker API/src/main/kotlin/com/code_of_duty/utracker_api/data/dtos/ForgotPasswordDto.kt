package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ForgotPasswordDto(
    @field:NotBlank(message = "Email is required")
    @field:Pattern(regexp = "\\S+", message = "Email cannot contain whitespace")
    @field:Email(message = "Email should be valid")
    val email : String,

    @field:NotBlank(message = "Token is required")
    @field:Pattern(regexp = "\\S+", message = "Token cannot contain whitespace")
    val token: String,

    @field:NotBlank(message = "Password is required")
    @field:Pattern(regexp = "\\S+", message = "Password cannot contain whitespace")
    val password : String,

    @field:NotBlank(message = "Confirm password is required")
    @field:Pattern(regexp = "\\S+", message = "Confirm password cannot contain whitespace")
    val confirmPassword : String
)