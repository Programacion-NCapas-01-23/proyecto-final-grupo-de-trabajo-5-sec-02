package com.code_of_duty.utracker_api.services.api.auth

import com.code_of_duty.utracker_api.data.dtos.ForgotPasswordDto
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.stereotype.Service

@Service
interface AuthService {
    fun registerStudent(registerDto: RegisterDto): Student
    fun authenticate(code: String, password: String): Student?
    fun isCodeTaken(code: String): Boolean
    //fun userAlreadyRegisteredInDegree()
    fun changePassword(forgotPasswordDto: ForgotPasswordDto)
    fun generateToken(student: Student): String
    fun validateToken(authToken: String): Boolean
}