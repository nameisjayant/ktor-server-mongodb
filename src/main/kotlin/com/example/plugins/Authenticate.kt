package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*


fun Application.configureAuthenticate() {

    install(Authentication) {
        bearer("auth-bearer") {
            realm = "note"
            authenticate { token ->
                UserIdPrincipal("id")
            }
        }
    }

}