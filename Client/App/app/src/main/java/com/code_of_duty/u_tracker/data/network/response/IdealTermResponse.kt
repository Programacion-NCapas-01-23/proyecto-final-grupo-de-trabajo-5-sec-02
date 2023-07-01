package com.code_of_duty.u_tracker.data.network.response

data class IdealTermResponse(
    val name: String,
    val cycleType: Int,
    val orderValue: Int,
    val subjects: List<SubjectsFromTermResponse>
)
