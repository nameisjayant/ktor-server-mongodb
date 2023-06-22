package com.example.features.user.domain.route

import com.example.KoinComponent
import com.example.auth.*
import com.example.features.user.domain.model.User
import com.example.plugins.MySession
import com.example.utils.ApiResponse
import com.example.utils.MessageResponse
import com.example.utils.errorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*


fun Application.userRoute(
    component: KoinComponent
) {

    routing {
        post("/register") {

            try {
                val userData = call.receive<User>()
                val hashPassword = hash(userData.password)
                val response = component.userRepository.registerUser(
                    User(email = userData.email, password = hashPassword)
                )
                val generateToken = generateToken(
                    userData, jwtConfig
                )
                call.respond(
                    status = HttpStatusCode.OK, ApiResponse(
                        generateToken,
                        listOf(response),
                        null,
                        MessageResponse(
                            201,
                            "User Register Successfully"
                        )
                    )
                )

            } catch (e: Exception) {
                errorResponse()
            }
        }

        delete("/user/{id}") {
            try {
                val userId = call.parameters["id"] ?: return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    "Pass User Id in path"
                )
                val isDeleted = component.userRepository.deleteUser(userId)
                if (isDeleted > 0)
                    call.respond(
                        status = HttpStatusCode.OK,
                        ApiResponse<User>(
                            null,
                            null,
                            null,
                            MessageResponse(
                                200,
                                "User Deleted Successfully"
                            )
                        )
                    )
                else
                    errorResponse(
                        400,
                        "User Not Found"
                    )
            } catch (e: java.lang.Exception) {
                errorResponse()
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
                            generateToken,
                            listOf(response),
                            null,
                            MessageResponse(
                                200,
                                "Logged Successfully"
                            )
                        )
                    )

                } else {
                    errorResponse(
                        statusCode = 400,
                        "Email and Password does not Matched"
                    )
                }

            } catch (e: java.lang.Exception) {
                errorResponse()
            }
        }
        put("/update/{id}") {
            val user = call.receive<User>()
            val id = call.parameters["id"]
            try {
                val hashPassword = hash(user.password)
                val result = component.userRepository.updateUser(id ?: "0", user.email, hashPassword)
                if (result > 0) {
                    call.respond(
                        status = HttpStatusCode.OK, ApiResponse<User>(
                            null,
                            listOf(user),
                            null,
                            MessageResponse(
                                200,
                                "Updated Successfully"
                            )
                        )
                    )
                } else {
                    errorResponse(
                        statusCode = 400,
                        "User ID Mismatched"
                    )
                }


            } catch (e: java.lang.Exception) {
                errorResponse()
            }
        }
    }

}