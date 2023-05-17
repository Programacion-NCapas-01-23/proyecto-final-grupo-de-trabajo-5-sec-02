package com.code_of_duty.utracker_api.utils


import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS256
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtils(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val expirationTime: Long
) {

    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTime)
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(HS256, jwtSecret)
            .compact()
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (e: Exception) {
            println("Invalid JWT token: ${e.message}")
        }
        return false
    }
}
