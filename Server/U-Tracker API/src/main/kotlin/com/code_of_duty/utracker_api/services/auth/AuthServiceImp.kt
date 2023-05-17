package com.code_of_duty.utracker_api.services.auth

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.data.models.Student
import com.code_of_duty.utracker_api.utils.PasswordUtils
import org.springframework.stereotype.Component

@Component
class AuthServiceImp(private val studentDao: StudentDao,
                     private val passwordUtils: PasswordUtils): AuthService{

    override fun isCodeTaken(code: String) = studentDao.existsByCode(code)

    override fun registerStudent(registerDto: RegisterDto, degree: String): Student {
        if (isCodeTaken(registerDto.code)) {
            throw IllegalArgumentException("Code already taken")
        }

        if (registerDto.password != registerDto.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }

        val hashPassword = passwordUtils.hashPassword(registerDto.password)
        val newStudent = Student(code = registerDto.code, username = registerDto.username, email = registerDto.email, hashPassword = hashPassword)
        return studentDao.save(newStudent)
    }

    override fun authenticate(code: String, password: String): Student? {
        if (!studentDao.existsByCode(code)) {
            return null
        }
        val student = studentDao.findById(code).orElse(null)
        if (student == null || !passwordUtils.verifyPassword(password, student.hashPassword)) {
            return null
        }
        return student
    }

}