package com.code_of_duty.utracker_api.controllers

import com.code_of_duty.utracker_api.data.dtos.LoginDto
import com.code_of_duty.utracker_api.data.dtos.RegisterDto
import com.code_of_duty.utracker_api.services.RegisterService
import com.code_of_duty.utracker_api.services.login.LoginService
import com.code_of_duty.utracker_api.utils.PasswordUtils
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (private val registerService: RegisterService,
                      private val passwordUtils: PasswordUtils,
                      private val loginService: LoginService
){

    @PostMapping("/register")
    fun register(@RequestBody registerDto: RegisterDto) :ResponseEntity<String>{
        if (registerService.isCodeTaken(registerDto.username)) {
            return ResponseEntity.badRequest().body("Code already taken")
        }

        // Check if passwords match
        if (registerDto.password != registerDto.confirmPassword) {
            return ResponseEntity.badRequest().body("Passwords do not match")
        }

        // Hash the password using PasswordUtils
        val hashedPassword = passwordUtils.hashPassword(registerDto.password)

        // Create a new user using UserService
        val newUser = registerService.registerStudent(registerDto, hashedPassword)

        // Return a success response with the new user's ID
        return ResponseEntity.ok("User successfully registered with ID ${newUser.code}")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<String> {
        // Get the user with the provided code
        val user = loginService.getStudentByCode(loginDto.code)

        // Check if user exists
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid code or password")
        }

        // Verify the provided password matches the stored password hash
        val isPasswordCorrect = passwordUtils.verifyPassword(loginDto.password, user.hashPassword)

        // If password is incorrect, return an error response
        if (!isPasswordCorrect) {
            return ResponseEntity.badRequest().body("Invalid code or password")
        }

        // Return a success response with the user's ID
        return ResponseEntity.ok("User successfully logged in with ID ${user.code}")
    }
}