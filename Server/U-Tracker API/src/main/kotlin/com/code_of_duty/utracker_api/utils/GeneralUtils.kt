package com.code_of_duty.utracker_api.utils

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Component

@Component
class GeneralUtils {
    fun extractJWT(request: HttpServletRequest): String {
        return request.getHeader("Authorization")?.removePrefix("Bearer ")
            ?: throw UnauthorizedException("Authorization token not provided")
    }
}