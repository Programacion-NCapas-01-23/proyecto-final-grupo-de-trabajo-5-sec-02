package com.code_of_duty.utracker_api.data.dtos

data class RegisterDto(
    val code : String,
    val username : String,
    val password : String,
    val confirmPassword : String
)
