package com.example.features.user.domain.route

import com.example.KoinComponent
import com.example.auth.*
import com.example.features.user.domain.model.User
import com.example.utils.ApiResponse
import com.example.utils.Constant
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*


fun Application.userRoute(
    component: KoinComponent
) {

    routing {
        post("/user") {

            try {
                val userData = call.receive<User>()
                val hashPassword = hash(userData.password)
                val response = component.userRepository.addUser(
                    User(email = userData.email, password = hashPassword)
                )
                val generateToken = generateToken(
                    userData, jwtConfig
                )
                call.respond(
                    status = HttpStatusCode.OK, ApiResponse(
                        statusCode = HttpStatusCode.OK,
                        generateToken,
                        listOf(response)
                    )
                )

            } catch (e: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest, ApiResponse<User>(
                        statusCode = HttpStatusCode.BadRequest,
                        null,
                        null
                    )
                )
            }
        }

        delete("/user/{id}") {
            try {
                val userId = call.parameters["id"] ?: return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    "Pass User Id in path"
                )
                val isDeleted = component.userRepository.deleteUser(userId)
                if (isDeleted)
                    call.respond(
                        status = HttpStatusCode.OK,
                        ApiResponse<User>(
                            HttpStatusCode.OK
                        )
                    )
                else
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        ApiResponse<User>(
                            HttpStatusCode.BadRequest
                        )
                    )
            } catch (e: java.lang.Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    ApiResponse<User>(
                        HttpStatusCode.BadRequest
                    )
                )
            }
        }

        post("/login") {
            val user = call.receive<User>()
            val hashPassword = hash(user.password)

            try {
                val response = component.userRepository.loginUser(user)
                if (response != null && response.password == hashPassword) {
                    call.sessions.set(MySession(response.id))
                    val generateToken = generateToken(
                        response, jwtConfig
                    )
                    call.respond(
                        status = HttpStatusCode.OK, ApiResponse(
                            statusCode = HttpStatusCode.OK,
                            generateToken,
                            listOf(response)
                        )
                    )

                } else {
                    call.respond(
                        status = HttpStatusCode.BadRequest, ApiResponse<User>(
                            statusCode = HttpStatusCode.BadRequest,
                            null,
                            null
                        )
                    )
                }

            } catch (e: java.lang.Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest, ApiResponse<User>(
                        statusCode = HttpStatusCode.BadRequest,
                        null,
                        null
                    )
                )
            }
        }
    }

}