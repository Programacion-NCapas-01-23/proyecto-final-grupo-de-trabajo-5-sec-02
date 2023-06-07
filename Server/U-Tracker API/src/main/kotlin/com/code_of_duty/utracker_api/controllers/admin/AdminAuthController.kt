package com.code_of_duty.utracker_api.controllers.admin

import com.code_of_duty.utracker_api.data.dtos.AdminLoginDto
import com.code_of_duty.utracker_api.data.dtos.LoginResDto
import com.code_of_duty.utracker_api.services.admin.auth.AdminAuthService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${admin.base-path}/auth")
class AdminAuthController(private val adminAuthService: AdminAuthService){
    @PostMapping("/login")
    fun login(@Valid @RequestBody adminLoginDto: AdminLoginDto) : ResponseEntity<LoginResDto> {
        val admin = adminAuthService.login(adminLoginDto)
        val token = adminAuthService.generateToken(admin)

        return ResponseEntity(LoginResDto(
            message = "Login successful",
            token = token,
        ), HttpStatus.OK)
    }
}