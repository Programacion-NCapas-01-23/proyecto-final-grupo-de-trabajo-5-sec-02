package com.code_of_duty.utracker_api.data.dtos

import java.util.*

data class DegreeDto(
    val id: UUID? = null,
    val name: String,
    val facultyId: UUID
)
