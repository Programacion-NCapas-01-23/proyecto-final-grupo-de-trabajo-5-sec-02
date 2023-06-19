package com.code_of_duty.utracker_api.controllers.api

import com.code_of_duty.utracker_api.data.dtos.*
import com.code_of_duty.utracker_api.services.api.auth.AuthService
import com.code_of_duty.utracker_api.services.api.student.StudentService
import com.code_of_duty.utracker_api.services.api.verificationToken.VerificationTokenService
import com.code_of_duty.utracker_api.utils.PasswordUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.web.bind.annotation.*
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@RestController
@RequestMapping("\${api.base-path}/auth")
@Tag(name = "API")
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

    @Operation(
        summary = "Register a new user",
        description = "Register a new user",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User successfully registered",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Username already taken",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto): ResponseEntity<Any> {
        if (authService.isCodeTaken(registerDto.code)) {
            return ResponseEntity(MessageDto("Code already taken"), HttpStatus.BAD_REQUEST)
        }

        val newUser = authService.registerStudent(registerDto)

        return ResponseEntity(MessageDto("User successfully registered with ID ${newUser.code}"), HttpStatus.OK)
    }


    @Operation(
        summary = "Login",
        description = "Login",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Login Successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LoginResDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid credentials",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<Any> {
        // Get the user with the provided code
        val user = authService.authenticate(loginDto.code, loginDto.password)
            ?: return ResponseEntity(MessageDto("Invalid credentials"), HttpStatus.BAD_REQUEST)

        val token = authService.generateToken(user)
        // Return a success response with the user's ID
        return ResponseEntity(LoginResDto("Login Successful", token), HttpStatus.OK)
    }

    @Operation(
        summary = "Forgot Password",
        description = "Forgot Password",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Password reset code sent to your email",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "User with email does not exist",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            )
        ]
    )
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

    @Operation(
        summary = "Change Password",
        description = "Change Password",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Password changed successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid verification token",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
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