package com.code_of_duty.u_tracker.ui.models

data class Subject(
    val code: String,
    val name: String,
    val uv: Int,
    val estimateGrade: Float,
    val prerequisiteID: List<Int>? = null,
)
