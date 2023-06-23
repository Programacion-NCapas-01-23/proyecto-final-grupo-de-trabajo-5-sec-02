package com.code_of_duty.u_tracker.data.network.request

data class RegisterRequest(
    val code: String,
    val username: String,
    val email: String,
    val password: String,
    val degreeId: String
)
