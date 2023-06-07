package com.code_of_duty.utracker_api.services.admin.auth

import com.code_of_duty.utracker_api.data.dao.AdminDao
import com.code_of_duty.utracker_api.data.dtos.AdminLoginDto
import com.code_of_duty.utracker_api.data.models.Admins
import com.code_of_duty.utracker_api.utils.JwtUtils
import com.code_of_duty.utracker_api.utils.PasswordUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AdminAuthServiceImp: AdminAuthService {
    @Autowired
    private lateinit var adminDao: AdminDao
    @Autowired
    private lateinit var jwtUtil: JwtUtils
    @Autowired
    private lateinit var passwordUtils: PasswordUtils
    override fun authenticate(username: String, password: String): Boolean {
        TODO("not yet implemented")
    }
    override fun generateToken(admin: Admins) = jwtUtil.generateToken(admin.username)

    override fun login(adminLoginDto: AdminLoginDto): Admins {
        val admin = adminDao.findByUsername(adminLoginDto.username)
            ?: throw IllegalArgumentException("Incorrect username")

        if (!passwordUtils.verifyPassword(adminLoginDto.password, admin.hashPassword))
            throw IllegalArgumentException("Incorrect password")

        return admin
    }

    override fun validateToken(authToken: String): Boolean {
        TODO("Not yet implemented")
    }
}