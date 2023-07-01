package com.code_of_duty.u_tracker.ui.models

data class ProvisionalTerm(
    val name: String,
    val cicleType: Int,
    val orderValue: Int,
    val subjects: List<ProvisionalSubject>
)
