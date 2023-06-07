package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotBlank

data class AdminLoginDto(
    @field:NotBlank(message = "Username is required")
    val username : String,
    @field:NotBlank(message = "Password is required")
    val password : String
)
