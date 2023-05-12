package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern


data class StudentRequestDto(
    @NotEmpty(message = "Code cannot be blank")
    @Pattern(regexp = "\\S+", message = "Code cannot contain whitespace")
    @Min(value = 8, message = "Code must be at least 8 characters long")
    @Max(value = 8, message = "Code must be at most 8 characters long")
    val code : String,
    @NotEmpty(message = "Username cannot be blank")
    @Pattern(regexp = "\\S+", message = "Username cannot contain whitespace")
    val username: String,
    @Pattern(regexp = "\\S+", message = "Image cannot contain whitespace")
    @Pattern(regexp = "^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|png)\$", message = "Image must be a valid URL")
    val image: String?
)