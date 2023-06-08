package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.*
import com.code_of_duty.utracker_api.services.admin.auth.AdminAuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${admin.base-path}/auth")
@Tag(name = "Admin")
class AdminAuthController(private val adminAuthService: AdminAuthService){
    @Operation(
        summary = "Login",
        description = "Login as an admin",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Login successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = LoginResDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
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
    fun login(@Valid @RequestBody adminLoginDto: AdminLoginDto) : ResponseEntity<LoginResDto> {
        val admin = adminAuthService.login(adminLoginDto)
        val token = adminAuthService.generateToken(admin)

        return ResponseEntity(LoginResDto(
            message = "Login successful",
            token = token,
        ), HttpStatus.OK)
    }

    @Operation(
        summary = "Register",
        description = "Register as an admin",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Register successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageDto::class
                        )
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorsDto::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/Register")
    fun register(@Valid @RequestBody adminRegisterDto: AdminRegisterDto) : ResponseEntity<MessageDto> {
        TODO("Implementar este método")
    }
}