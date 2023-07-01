package com.code_of_duty.u_tracker.ui.models

data class ProvisionalSubject(
    val code: String,
    val name: String,
    val uv: Int,
    val estimateGrade: Double,
    val prerequisiteId: List<Int>? = null,
)
