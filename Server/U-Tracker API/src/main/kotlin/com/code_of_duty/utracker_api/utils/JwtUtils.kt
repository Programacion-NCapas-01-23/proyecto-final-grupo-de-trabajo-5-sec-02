package com.code_of_duty.utracker_api.utils

import com.sun.org.apache.xml.internal.security.algorithms.Algorithm
import com.sun.org.apache.xml.internal.security.algorithms.SignatureAlgorithm
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtUtils(
    @Value("\${jwt.secret}") private val jwtSecret: String,
    @Value("\${jwt.expiration}") private val expirationTime: Long,
    @Value("\${jwt.public-key}") private val publicKey: String,
    @Value("\${jwt.private-key}") private val privateKey: String
) {
    private val algorithm = Algorithm.RSA256(
        publicKey.toByteArray(Charsets.UTF_8),
        privateKey.toByteArray(Charsets.UTF_8)
    )

    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTime)
        return JWT.create()
            .withSubject(username)
            .withIssuedAt(now)
            .withExpiresAt(expiryDate)
            .sign(algorithm)
    }

    fun validateToken(authToken: String): Boolean {
        try {
            JWT.require(algorithm).build().verify(authToken)
            return true
        } catch (e: JWTVerificationException) {
            println("Invalid JWT token: ${e.message}")
        }
        return false
    }
}
