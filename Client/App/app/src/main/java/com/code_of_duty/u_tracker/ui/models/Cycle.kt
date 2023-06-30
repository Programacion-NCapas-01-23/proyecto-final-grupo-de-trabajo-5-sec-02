package com.code_of_duty.u_tracker.ui.models

import com.code_of_duty.u_tracker.enums.CycleType

data class Cycle(
    val name: String,
    val cycleType: CycleType,
    val orderValue: Int,
    val subjects: List<Subject>
)
