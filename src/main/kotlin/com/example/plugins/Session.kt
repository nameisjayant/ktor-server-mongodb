package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*


fun Application.configureSession() {
    install(Sessions) {
        cookie<MySession>("ID") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}
data class MySession(
    val id:String
)