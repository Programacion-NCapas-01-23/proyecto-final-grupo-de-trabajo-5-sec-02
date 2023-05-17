package com.code_of_duty.utracker_api.services.auth

import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.stereotype.Service

@Service
interface AuthService {
    fun registerStudent(registerDto: RegisterDto, degree: String): Student
    fun authenticate(code: String, password: String): Student?
    fun isCodeTaken(code: String): Boolean
    fun generateToken(student: Student): String
    fun validateToken(authToken: String): Boolean
}