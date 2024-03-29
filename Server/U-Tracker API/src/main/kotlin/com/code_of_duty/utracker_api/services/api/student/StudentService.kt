package com.code_of_duty.utracker_api.services.api.student

import com.code_of_duty.utracker_api.data.dtos.ChangePasswordDto
import com.code_of_duty.utracker_api.data.dtos.StudentDto
import com.code_of_duty.utracker_api.data.dtos.StudentRequestDto
import com.code_of_duty.utracker_api.data.models.Student
import org.springframework.stereotype.Service

@Service
interface StudentService {
    fun getStudent(code: String): StudentDto?

    fun updateStudent(code: String, student: StudentRequestDto)

    fun changePassword(code: String, changePasswordDto: ChangePasswordDto)
    fun findByEmail(email: String): Student?
}