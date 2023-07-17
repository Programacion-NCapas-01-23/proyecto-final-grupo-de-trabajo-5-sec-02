package com.code_of_duty.utracker_api.utils

import com.code_of_duty.utracker_api.data.dao.StudentDao
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class GeneralUtils {
    @Autowired
    private lateinit var jwtUtils: JwtUtils
    @Autowired
    private lateinit var studentDao: StudentDao
    fun extractJWT(request: HttpServletRequest): String {
        val token = request.getHeader("Authorization")?.removePrefix("Bearer ")
            ?: throw UnauthorizedException("Authorization token not provided")

        !jwtUtils.validateToken(token) && throw UnauthorizedException("Invalid token")

        return jwtUtils.getIdFromToken(token)
    }
}