package com.code_of_duty.utracker_api.services.auth

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.data.models.Student
import com.code_of_duty.utracker_api.utils.PasswordUtils
import org.springframework.stereotype.Service

@Service
class AuthService(private val studentDao: StudentDao, private val passwordUtils: PasswordUtils) {

    fun getStudentByCode(code: String): Student? {
        return studentDao.findByCode(code)
    }

    fun isCodeTaken(code: String): Boolean {
        return studentDao.existsByCode(code)
    }

    fun registerStudent(registerDto: RegisterDto, degree: String): Student {
        if (isCodeTaken(registerDto.code)) {
            throw IllegalArgumentException("Code already taken")
        }

        if (registerDto.password != registerDto.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }

        val hashPassword = passwordUtils.hashPassword(registerDto.password)
        val newStudent = Student(code = registerDto.code, username = registerDto.username, hashPassword = hashPassword)
        return studentDao.save(newStudent)
    }
}
