package com.code_of_duty.u_tracker.ui.models
data class Cycle(
    val name: String,
    val cycleType: Int,
    val orderValue: Int,
    val subjects: List<Subject>
)
