package com.example.auth


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.features.user.domain.model.User
import java.util.*


data class JwtConfig(val secret: String, val issuer: String, val audience: String)

fun generateToken(user: User, jwtConfig: JwtConfig): String {
    val now = Date()
    val expiresAt = Date(now.time + 24 * 60 * 60 * 1000) // Token expires in 24 hours

    return JWT.create()
        .withSubject(user.id)
        .withAudience(jwtConfig.audience)
        .withIssuer(jwtConfig.issuer)
        .withExpiresAt(expiresAt)
        .sign(Algorithm.HMAC256(jwtConfig.secret))
}

val jwtConfig = JwtConfig(
    secret = "84894844",
    issuer = "Server",
    audience = "Note"
)