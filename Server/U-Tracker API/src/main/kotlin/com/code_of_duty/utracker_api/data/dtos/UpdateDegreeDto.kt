package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty

data class UpdateDegreeDto(
    @field:NotEmpty(message = "id cannot be empty")
    val id: String,
    @field:NotEmpty(message = "name cannot by empty")
    val name: String,
    @field:NotEmpty(message = "faculty id cannot be empty")
    val facultyId: String
)
