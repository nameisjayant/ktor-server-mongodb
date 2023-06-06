package com.example.plugins

import com.example.module.koinModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.startKoin() {
    install(Koin) {
        slf4jLogger()
        modules(koinModule)
    }
}