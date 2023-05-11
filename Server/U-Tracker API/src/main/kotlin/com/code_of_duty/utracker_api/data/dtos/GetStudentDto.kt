package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern


data class GetStudentDto(
    @NotBlank(message = "Code cannot be blank")
    @Pattern(regexp = "\\S+", message = "Code cannot contain whitespace")
    @Min(value = 8, message = "Code must be at least 8 characters long")
    @Max(value = 8, message = "Code must be at most 8 characters long")
    val code : String,
    @NotBlank(message = "JWT cannot be blank")
    @Pattern(regexp = "\\S+", message = "Code cannot contain whitespace")
    val jwt : String
)