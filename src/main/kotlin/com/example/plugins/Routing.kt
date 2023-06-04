package com.example.plugins

import com.example.db.DatabaseConnection
import com.example.model.User
import com.example.model.UserResponse
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import java.lang.Exception

fun Application.configureRouting(
    db: DatabaseConnection
) {

    routing {

        get("/") {
            call.respond("Hello world")
        }

        route("/user") {
            post {
                try {
                    val userData = call.receive<User>()
                    val response = db.addUser(
                        userData
                    )
                    call.respond(
                        status = HttpStatusCode.OK, UserResponse(
                            status = true,
                            statusCode = 200,
                            message = "Success",
                            listOf(response)
                        )
                    )

                } catch (e: Exception) {
                    call.respond(
                        status = HttpStatusCode.BadRequest, UserResponse(
                            status = true,
                            statusCode = 400,
                            message = "Please pass name and age in the body request",
                            emptyList()
                        )
                    )
                }
            }



            get {
                try {
                    call.respond(db.getAllUsers())
                } catch (e: Exception) {
                    application.log.error("Failed to get user", e.message)
                }
            }
        }
    }

}
