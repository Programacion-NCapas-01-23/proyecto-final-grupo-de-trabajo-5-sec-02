package com.code_of_duty.utracker_api.data.dtos

import io.swagger.v3.oas.annotations.media.ExampleObject
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class StudentRequestDto(

    @field:NotEmpty(message = "Code cannot be blank")
    @field:Pattern(regexp = "\\S+", message = "Code cannot contain whitespace")
    @field:Size(min = 8, max = 8, message = "Code must be 8 characters long")
    val code : String,

    @field:NotEmpty(message = "Username cannot be blank")
    val username: String,

    @field:Pattern(regexp = "\\S+", message = "Image cannot contain whitespace")
    @field:Pattern(regexp = "^(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|png)\$", message = "Image must be a valid URL")
    val image: String?
)