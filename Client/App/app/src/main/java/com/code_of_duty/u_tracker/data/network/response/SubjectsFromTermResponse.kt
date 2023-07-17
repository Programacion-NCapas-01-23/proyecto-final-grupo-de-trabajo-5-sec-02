package com.code_of_duty.u_tracker.data.network.response

data class SubjectsFromTermResponse(
    val code: String,
    val name: String,
    val grade: Float? = null,
    val uv: Int,
    val correlative: Int,
    val prerequisiteID: List<Int>? = emptyList(),
)
