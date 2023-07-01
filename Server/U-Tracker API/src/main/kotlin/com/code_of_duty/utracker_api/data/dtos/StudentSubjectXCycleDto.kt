package com.code_of_duty.utracker_api.data.dtos

data class StudentSubjectXCycleDto (
    val code: String,
    val name: String,
    val uv: Int,
    val estimateGrade: Int?,
    val prerequisiteID: List<Int>?
)