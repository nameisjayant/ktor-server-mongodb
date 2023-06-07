package com.example.plugins

import com.example.auth.MySession
import io.ktor.server.application.*
import io.ktor.server.sessions.*


fun Application.configureSession() {
    install(Sessions) {
        cookie<MySession>("ID") {
            cookie.extensions["SameSite"] = "lax"
        }
    }
}