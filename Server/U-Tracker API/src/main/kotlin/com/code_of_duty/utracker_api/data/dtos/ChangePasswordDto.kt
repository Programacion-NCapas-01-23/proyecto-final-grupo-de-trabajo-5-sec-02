package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ChangePasswordDto(

    @field:NotBlank(message = "Old password is required")
    @field:Pattern(regexp = "\\S+", message = "Old password cannot contain whitespace")
    val oldPassword: String,

    @field:NotBlank(message = "New password is required")
    @field:Pattern(regexp = "\\S+", message = "new password cannot contain whitespace")
    val newPassword: String,

    @field:NotBlank(message = "Confirm password is required")
    @field:Pattern(regexp = "\\S+", message = "Confirm password cannot contain whitespace")
    val confirmPassword: String
)
