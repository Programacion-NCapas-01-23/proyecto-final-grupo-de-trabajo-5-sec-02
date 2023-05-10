package com.code_of_duty.utracker_api.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordUtils {
    private val encoder = BCryptPasswordEncoder()

    fun hashPassword(password: String): String {
        return encoder.encode(password)
    }

    fun verifyPassword(password: String, hash: String): Boolean {
        return encoder.matches(password, hash)
    }
}