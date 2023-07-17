package com.code_of_duty.utracker_api.services.admin.auth

import com.code_of_duty.utracker_api.data.dao.AdminDao
import com.code_of_duty.utracker_api.data.dtos.AdminLoginDto
import com.code_of_duty.utracker_api.data.dtos.AdminRegisterDto
import com.code_of_duty.utracker_api.data.models.Admins
import com.code_of_duty.utracker_api.utils.JwtUtils
import com.code_of_duty.utracker_api.utils.PasswordUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class AdminAuthServiceImp: AdminAuthService {
    @Autowired
    private lateinit var adminDao: AdminDao
    @Autowired
    private lateinit var jwtUtil: JwtUtils
    @Autowired
    private lateinit var passwordUtils: PasswordUtils
    override fun register(adminRegisterDto: AdminRegisterDto): Admins {
        if(adminDao.existsByUsername(adminRegisterDto.username))
            throw IllegalArgumentException("Username already exists")

        if (adminDao.existsByEmail(adminRegisterDto.email))
            throw IllegalArgumentException("Email already exists")

        val hashPassword = passwordUtils.hashPassword(adminRegisterDto.password)
        val admin = Admins(
            name = adminRegisterDto.name,
            username = adminRegisterDto.username,
            email = adminRegisterDto.email,
            hashPassword = hashPassword
        )
        return adminDao.save(admin)
    }

    override fun generateToken(admin: Admins): String {
        val role = "admin"
        return jwtUtil.generateToken(admin.id.toString(), role)
    }


    override fun login(adminLoginDto: AdminLoginDto): Admins {
        val admin = adminDao.findByUsername(adminLoginDto.username)
            ?: throw IllegalArgumentException("Incorrect username")

        if (!passwordUtils.verifyPassword(adminLoginDto.password, admin.hashPassword))
            throw IllegalArgumentException("Incorrect password")

        return admin
    }

    override fun validateToken(authToken: String) = jwtUtil.validateToken(authToken)

    override fun exists(id: UUID) = adminDao.existsById(id)
}