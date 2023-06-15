package com.code_of_duty.utracker_api.data.dtos

import java.util.UUID

data class ScheduleswithIDDto(
    val id: UUID,
    val collection: Int,
    val subject: UUID,
    val classTime: UUID
)
