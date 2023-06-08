package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.ForgotPasswordDto
import com.code_of_duty.utracker_api.data.dtos.LoginDto
import com.code_of_duty.utracker_api.data.dtos.MessageDto
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.services.auth.AuthService
import com.code_of_duty.utracker_api.services.student.StudentService
import com.code_of_duty.utracker_api.services.verificationToken.VerificationTokenService
import com.code_of_duty.utracker_api.utils.PasswordUtils
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.util.StreamUtils
import org.springframework.web.bind.annotation.*
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@RestController
@RequestMapping("\${api.base-path}/auth")
@Tag(name = "API", description = "API operations")
class AuthController (private val passwordUtils: PasswordUtils){

    @Autowired
    lateinit var authService: AuthService
    @Autowired
    lateinit var verificationTokenService: VerificationTokenService
    @Autowired
    private lateinit var mailSender: JavaMailSender
    @Autowired
    lateinit var studentService: StudentService
    @Autowired
    private lateinit var templateEngine: TemplateEngine
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

        val token = authService.generateToken(user)
        // Return a success response with the user's ID
        return ResponseEntity.ok("User successfully logged in with ID ${user.code} with token: ${token}")
    }

    @GetMapping("/getVerificationToken")
    fun forgotPassword(@Email email: String): ResponseEntity<MessageDto> {
        // Check if user exists
        val student = studentService.findByEmail(email)
            ?: return ResponseEntity.badRequest().body(MessageDto("User with email $email does not exist"))

        val verifyToken = verificationTokenService.createVerificationToken(student.code)

        // Send email with verification token
        val context = Context()
        context.setVariable("verifyToken", verifyToken)
        val html = templateEngine.process("email_template", context)
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "utf-8")
        helper.setTo(student.email)
        helper.setSubject("Password Reset code")
        message.setText(html, "utf-8", "html")
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