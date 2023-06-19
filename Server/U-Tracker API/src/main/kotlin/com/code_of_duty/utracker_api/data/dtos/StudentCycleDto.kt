package com.code_of_duty.utracker_api.data.dtos

import java.util.UUID

data class StudentCycleDto(
    val id: String?,
    val studentUUID: UUID,
    val userCycleId: String,
    val subjects: List<UUID>
)
