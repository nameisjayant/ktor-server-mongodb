package com.example.utils

import com.example.auth.secretKey
import io.jsonwebtoken.Jwts
import java.util.regex.Pattern


fun findUserByFromToken(token: String): String? {
    val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
    return claims.subject

}

fun validateEmail(email: String) = Pattern.compile(Constant.EMAIL_PATTERN).matcher(email).matches()
