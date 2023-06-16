package com.code_of_duty.utracker_api.data.dtos

import jakarta.validation.constraints.NotEmpty
import java.util.*

data class SchedulesDto(
    val id: String?,
    @field:NotEmpty(message = "Collection cannot be empty")
    val collection: Int,
    @field:NotEmpty(message = "Subject cannot be empty")
    val subject: String,
    @field:NotEmpty(message = "Class time cannot be empty")
    val classTime: UUID
)
