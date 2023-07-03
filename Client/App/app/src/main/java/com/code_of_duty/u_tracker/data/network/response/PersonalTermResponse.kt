package com.code_of_duty.u_tracker.data.network.response

data class PersonalTermResponse(
    val id: String,
    val studentCode: String,
    val cycleType: Int,
    val year: Int,
    val subjects: List<SubjectsFromTermResponse>? = null,
)
