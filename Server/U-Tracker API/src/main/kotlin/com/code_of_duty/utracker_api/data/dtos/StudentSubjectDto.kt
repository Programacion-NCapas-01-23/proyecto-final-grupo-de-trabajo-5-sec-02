package com.code_of_duty.utracker_api.data.dtos


data class StudentSubjectDto (
    val code: String,
    val correlative: Int,
    val name: String,
    val uv: Int,
    val estimateGrade: Int?,
    val prerequisiteID: List<Int>?
)