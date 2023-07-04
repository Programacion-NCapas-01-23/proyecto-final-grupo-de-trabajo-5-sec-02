package com.code_of_duty.utracker_api.data.dtos

data class SubjectDto(
    val code: String,
    val name: String,
    val uv: Int,
    val cycleRelation: List<CycleRelationDto>?
)
