package com.code_of_duty.utracker_api.services.api.auth

import com.code_of_duty.utracker_api.data.dao.StudentDao
import com.code_of_duty.utracker_api.data.dtos.ForgotPasswordDto
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.data.models.Student
import com.code_of_duty.utracker_api.services.api.degree.DegreeService
import com.code_of_duty.utracker_api.services.api.verificationToken.VerificationTokenService
import com.code_of_duty.utracker_api.utils.ExceptionNotFound
import com.code_of_duty.utracker_api.utils.JwtUtils
import com.code_of_duty.utracker_api.utils.PasswordUtils
import org.springframework.stereotype.Component
import java.util.*

@Component
class AuthServiceImp(
    private val studentDao: StudentDao,
    private val passwordUtils: PasswordUtils,
    private val jwtUtils: JwtUtils,
    private val verificationTokenService: VerificationTokenService,
    private val degreeService: DegreeService
): AuthService{

    override fun isCodeTaken(code: String) = studentDao.existsByCode(code)
    override fun generateToken(student: Student): String {
        val role = "student"
        return jwtUtils.generateToken(student.code, role)
    }


    override fun validateToken(authToken: String) = jwtUtils.validateToken(authToken)

    override fun registerStudent(registerDto: RegisterDto): Student {
        if (isCodeTaken(registerDto.code)) {
            throw IllegalArgumentException("Code already taken")
        }

        val hashPassword = passwordUtils.hashPassword(registerDto.password)
        val degree = degreeService.findById(
            UUID.fromString(registerDto.degreeId)
            ?: throw ExceptionNotFound("Degree not found"))

        val newStudent = Student(
            code = registerDto.code,
            username = registerDto.username,
            email = registerDto.email,
            hashPassword = hashPassword,
            degree = degree
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

        if (token.student.email != student.email) {
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