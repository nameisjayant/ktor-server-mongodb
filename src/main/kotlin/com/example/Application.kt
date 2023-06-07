package com.example

import com.example.auth.JwtConfig
import com.example.auth.jwtConfig
import com.example.features.notes.domain.route.noteRoute
import com.example.features.user.domain.route.userRoute
import com.example.plugins.configureAuthenticate
import com.example.plugins.configureSerialization
import com.example.plugins.configureSession
import com.example.plugins.startKoin
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val component = KoinComponent()
    startKoin()
    configureSerialization()
    configureAuthenticate(
        jwtConfig
    )
    configureSession()
    userRoute(component)
    noteRoute()

}
