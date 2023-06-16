package com.code_of_duty.utracker_api.utils

import com.code_of_duty.utracker_api.data.enums.CycleType
import com.code_of_duty.utracker_api.data.enums.Days
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class GeneralUtils {
    @Autowired
    private lateinit var jwtUtils: JwtUtils
    fun extractJWT(request: HttpServletRequest): String {
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")
            ?: throw UnauthorizedException("Authorization token not provided")

        !jwtUtils.validateToken(token) && throw UnauthorizedException("Invalid token")

        return jwtUtils.getIdFromToken(token)
    }
}