package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class AdminRegisterDto(
    @field:NotEmpty(message = "Name is required")
    val name : String,
    @field:NotEmpty(message = "Username is required")
    val username : String,
    @field:NotEmpty(message = "Email is required")
    @field:Email(message = "Email is not valid")
    val email : String,
    @field:NotEmpty(message = "Password is required")
    @field:Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+$).{8,}$",
        message = "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character")
    val password : String,
)
