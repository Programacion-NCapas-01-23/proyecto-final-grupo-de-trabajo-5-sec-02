package com.code_of_duty.u_tracker.data.network.request

data class ChangePasswordRequest(
    val email: String,
    val token: String,
    val password: String,
    val confirmPassword: String
)
