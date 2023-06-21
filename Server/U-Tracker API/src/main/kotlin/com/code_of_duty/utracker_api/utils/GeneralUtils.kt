package com.code_of_duty.utracker_api.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class GeneralUtils {
    @Autowired
    private lateinit var jwtUtils: JwtUtils
    fun extractJWT(request: HttpServletRequest): String {
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")
            ?: throw UnauthorizedException("Authorization token not provided")

        if (!jwtUtils.validateToken(token)) {
            throw UnauthorizedException("Invalid token")
        }

        return jwtUtils.getIdFromToken(token)
    }

    fun extractUserDegreeFromToken(request: HttpServletRequest): UUID {
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")
            ?: throw UnauthorizedException("Authorization token not provided")

        !jwtUtils.validateToken(token) && throw UnauthorizedException("Invalid token")

        return UUID.fromString(jwtUtils.getUserDegreeFromToken(token))
    }
}