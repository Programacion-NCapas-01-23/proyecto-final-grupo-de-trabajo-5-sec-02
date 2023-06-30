package com.code_of_duty.utracker_api.utils


import io.jsonwebtoken.Claims
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

    fun generateToken(code: String, role: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTime)
        return Jwts.builder()
            .setSubject(code)
            .claim("role", role) // Include the role in the token claims
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

    fun getIdFromToken(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun extractUserTypeFromToken(token: String): String {
        val claims = extractAllClaims(token)
        return claims["userType"] as String
    }

    fun extractUserCodeFromToken(token: String): String {
        val claims = extractAllClaims(token)
        return claims.subject
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body
    }
}
