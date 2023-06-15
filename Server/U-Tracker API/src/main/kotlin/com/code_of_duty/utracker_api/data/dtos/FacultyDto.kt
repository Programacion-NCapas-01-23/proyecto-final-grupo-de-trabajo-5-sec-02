package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern

data class FacultyDto(
    @field:NotEmpty(message = "name cannot be empty")
    val name: String,
    @field:NotEmpty(message = "description cannot be empty")
    val description: String,
    @field:Pattern(
        regexp = "^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|png)\$",
        message = "logo must be a valid URL"
    )
    val logo: String?
)
