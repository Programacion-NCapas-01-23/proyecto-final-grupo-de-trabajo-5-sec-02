package com.code_of_duty.utracker_api.data.dtos

import java.util.*

data class SchedulesDto(
    val collection: Int,
    val subject: UUID,
    val classTime: UUID
)
