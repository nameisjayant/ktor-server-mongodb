package com.example.auth


import io.ktor.util.*
import javax.crypto.Mac
import java.util.*
import javax.crypto.spec.SecretKeySpec

val hashKey = hex(System.getenv("SECRET_KEY"))

val hmaKey = SecretKeySpec(hashKey, "HmacSHA1")

fun hash(password: String): String {
    val hMac = Mac.getInstance("HmacSHA1")
    hMac.init(hmaKey)
    return hex(hMac.doFinal(password.toByteArray(Charsets.UTF_8)))
}
