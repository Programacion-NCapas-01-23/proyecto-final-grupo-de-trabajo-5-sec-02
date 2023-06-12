package com.code_of_duty.utracker_api.utils

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

    fun getDay(day: Int, position: Int = -1): Days {
        return when(day){
            0 -> Days.MONDAY
            1 -> Days.TUESDAY
            2 -> Days.WEDNESDAY
            3 -> Days.THURSDAY
            4 -> Days.FRIDAY
            5 -> Days.SATURDAY
            else -> {
                if(position != -1)
                    throw IllegalArgumentException("Invalid day in object with index $position")
                else
                    throw IllegalArgumentException("Invalid day")
            }
        }
    }
}