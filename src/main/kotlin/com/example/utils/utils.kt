package com.example.utils

import com.example.auth.secretKey
import io.jsonwebtoken.Jwts


fun findUserByFromToken(token: String): String? {
    val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
    return claims.subject

}