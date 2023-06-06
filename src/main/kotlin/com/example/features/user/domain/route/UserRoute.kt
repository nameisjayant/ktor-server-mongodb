package com.example.features.user.domain.route

import com.example.KoinComponent
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
            authenticate("auth-bearer") {

                post {
                    val token = call.principal<UserIdPrincipal>()?.name ?: "none"
                    try {
                        val userData = call.receive<User>()
                        val response = component.userRepository.addUser(
                            userData
                        )
                        call.respond(
                            status = HttpStatusCode.OK, ApiResponse(
                                status = true,
                                statusCode = HttpStatusCode.OK,
                                message = "Success",
                                token,
                                listOf(response)
                            )
                        )

                    } catch (e: Exception) {
                        call.respond(
                            status = HttpStatusCode.BadRequest, ApiResponse<User>(
                                status = true,
                                statusCode = HttpStatusCode.BadRequest,
                                message = "Please pass email and password in the body request",
                                null,
                                null
                            )
                        )
                    }
                }
            }
        }
    }

}