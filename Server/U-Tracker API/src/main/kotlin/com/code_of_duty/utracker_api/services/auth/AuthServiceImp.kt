package com.code_of_duty.utracker_api.services.auth

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.ForgotPasswordDto
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.data.models.Student
import com.code_of_duty.utracker_api.services.verificationToken.VerificationTokenService
import com.code_of_duty.utracker_api.utils.JwtUtils
import com.code_of_duty.utracker_api.utils.PasswordUtils
import org.springframework.stereotype.Component

@Component
class AuthServiceImp(private val studentDao: StudentDao,
                     private val passwordUtils: PasswordUtils, private val jwtUtils: JwtUtils, private val verificationTokenService: VerificationTokenService): AuthService{

    override fun isCodeTaken(code: String) = studentDao.existsByCode(code)
    override fun generateToken(student: Student): String {
        val username = student.username // Assuming the username is used as the subject for the token
        return jwtUtils.generateToken(username)
    }

    override fun validateToken(authToken: String): Boolean {
        return jwtUtils.validateToken(authToken)
    }

    override fun registerStudent(registerDto: RegisterDto, degree: String): Student {
        if (isCodeTaken(registerDto.code)) {
            throw IllegalArgumentException("Code already taken")
        }

        if (registerDto.password != registerDto.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }

        val hashPassword = passwordUtils.hashPassword(registerDto.password)
        val newStudent = Student(
            code = registerDto.code,
            username = registerDto.username,
            email = registerDto.email,
            hashPassword = hashPassword
        )
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

    override fun changePassword(forgotPasswordDto: ForgotPasswordDto) {
        val student = studentDao.findByEmail(forgotPasswordDto.email)
            ?: throw IllegalArgumentException("Email not found")

        if (forgotPasswordDto.password != forgotPasswordDto.confirmPassword) {
            throw IllegalArgumentException("Passwords do not match")
        }

        val token = verificationTokenService.findByToken(forgotPasswordDto.token)
            ?: throw IllegalArgumentException("Invalid token")

        if (token.student != student) {
            throw IllegalArgumentException("Invalid token")
        }

        if (token.expiryDate.isBefore(java.time.LocalDateTime.now())) {
            throw IllegalArgumentException("Token expired")
        }

        val hashPassword = passwordUtils.hashPassword(forgotPasswordDto.password)

        val studentWithChangedPassword = student.copy(hashPassword = hashPassword)

        studentDao.save(studentWithChangedPassword)

        verificationTokenService.deleteVerificationToken(token.token)
    }
}