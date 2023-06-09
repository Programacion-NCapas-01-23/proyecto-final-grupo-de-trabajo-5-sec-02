package com.code_of_duty.utracker_api.services.admin.auth

import com.code_of_duty.utracker_api.data.dtos.AdminLoginDto
import com.code_of_duty.utracker_api.data.dtos.AdminRegisterDto
import com.code_of_duty.utracker_api.data.models.Admins
import org.springframework.stereotype.Service

@Service
interface AdminAuthService {
    fun login(adminLoginDto: AdminLoginDto): Admins
    fun generateToken(admin: Admins): String
    fun validateToken(authToken: String): Boolean
    fun register(adminRegisterDto: AdminRegisterDto): Admins
}