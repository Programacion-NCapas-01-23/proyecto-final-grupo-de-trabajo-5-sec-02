package com.code_of_duty.utracker_api.controllers

import com.code_of_duty.utracker_api.data.dtos.ForgotPasswordDto
import com.code_of_duty.utracker_api.data.dtos.LoginDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.services.auth.AuthService
import com.code_of_duty.utracker_api.services.student.StudentService
import com.code_of_duty.utracker_api.services.verificationToken.VerificationTokenService
import com.code_of_duty.utracker_api.utils.PasswordUtils
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController (private val passwordUtils: PasswordUtils){

    @Autowired
    lateinit var authService: AuthService
    @Autowired
    lateinit var verificationTokenService: VerificationTokenService
    @Autowired
    private lateinit var mailSender: JavaMailSender
    @Autowired
    lateinit var studentService: StudentService
    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto) :ResponseEntity<String>{
        if (authService.isCodeTaken(registerDto.username)) {
            return ResponseEntity.badRequest().body("Code already taken")
        }

        // Check if passwords match
        if (registerDto.password != registerDto.confirmPassword) {
            return ResponseEntity.badRequest().body("Passwords do not match")
        }

        // Hash the password using PasswordUtils
        val hashedPassword = passwordUtils.hashPassword(registerDto.password)

        // Create a new user using UserService
        val newUser = authService.registerStudent(registerDto, hashedPassword)

        // Return a success response with the new user's ID
        return ResponseEntity.ok("User successfully registered with ID ${newUser.code}")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<String> {
        // Get the user with the provided code
        val user = authService.authenticate(loginDto.code, loginDto.password)
            ?: return ResponseEntity.badRequest().body("Invalid code or password")

        // Check if user exists

        // Verify the provided password matches the stored password hash
        val isPasswordCorrect = passwordUtils.verifyPassword(loginDto.password, user.hashPassword)

        // If password is incorrect, return an error response
        if (!isPasswordCorrect) {
            return ResponseEntity.badRequest().body("Invalid code or password")
        }

        // Return a success response with the user's ID
        return ResponseEntity.ok("User successfully logged in with ID ${user.code}")
    }

    @GetMapping("/getVerificationToken")
    fun forgotPassword(@Email email: String): ResponseEntity<MessageDto> {
        // Check if user exists
        val student = studentService.findByEmail(email)
            ?: return ResponseEntity.badRequest().body(MessageDto("User with email $email does not exist"))

        val verifyToken = verificationTokenService.createVerificationToken(student.code)

        // Send email with verification token

        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "utf-8")
        helper.setTo(student.email)
        helper.setSubject("Password Reset code")
        message.setText("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Token de verificación</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Token de verificación</h1>\n" +
                "    <p>¡Bienvenido al correo de recuperacion! A continuación, encontrarás tu token de verificación de 6 dígitos:</p>\n" +
                "    <h2 style=\"background-color: #f5f5f5; padding: 10px; display: inline-block;\">${verifyToken.token}</h2>\n" +
                "    <p>Utiliza este token para completar el proceso de verificación en nuestra plataforma.</p>\n" +
                "</body>\n" +
                "</html>\n", "utf-8", "html")
        mailSender.send(message)

        return ResponseEntity(MessageDto("Password reset code sent to your email"), HttpStatus.OK)
    }

    @PatchMapping("/changePassword")
    fun changePassword(
        @RequestBody
        @Valid
        forgotPasswordDto: ForgotPasswordDto
    ): ResponseEntity<MessageDto>{
        authService.changePassword(forgotPasswordDto)
        return ResponseEntity(MessageDto("Password changed successfully"), HttpStatus.OK)
    }
}