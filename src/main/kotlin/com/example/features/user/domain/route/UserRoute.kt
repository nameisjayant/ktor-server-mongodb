package com.example.features.user.domain.route

import com.example.KoinComponent
import com.example.auth.JwtConfig
import com.example.auth.generateToken
import com.example.auth.jwtConfig
import com.example.features.user.domain.model.User
import com.example.utils.ApiResponse
import com.example.utils.Constant
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.userRoute(
    component: KoinComponent
) {

    routing {
        route(Constant.USER) {
            post {
                val token = call.principal<UserIdPrincipal>()?.name ?: "none"
                try {
                    val userData = call.receive<User>()
                    val response = component.userRepository.addUser(
                        userData
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

            delete("/{id}") {
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
        }
    }

}