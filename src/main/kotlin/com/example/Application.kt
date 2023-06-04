package com.example

import com.example.db.DatabaseConnection
import com.example.features.notes.domain.route.noteRoute
import com.example.features.user.domain.route.userRoute
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val db = DatabaseConnection()
    startKoin()
    configureSerialization()
    configureRouting(db)
    noteRoute()
    userRoute()
}
