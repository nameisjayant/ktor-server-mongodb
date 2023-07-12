package com.example.features.user.domain.route

import com.example.KoinComponent
import com.example.auth.*
import com.example.features.user.domain.model.User
import com.example.routings.LoginRoute
import com.example.routings.RegisterRoute
import com.example.routings.UserRoute
import com.example.utils.ApiResponse
import com.example.utils.Constant.EMAIL_ALREADY_EXITS
import com.example.utils.Constant.EMAIL_DID_NOT_CHANGED
import com.example.utils.Constant.EMAIL_IS_NOT_VALID
import com.example.utils.Constant.EMAIL_PASSWORD_DOES_NOT_MATCHED
import com.example.utils.Constant.EMAIL_SHOULD_NOT_EMPTY
import com.example.utils.Constant.LOGGED_SUCCESSFULLY
import com.example.utils.Constant.PASSWORD_SHOULD_NOT_EMPTY
import com.example.utils.MessageResponse
import com.example.utils.errorResponse
import com.example.utils.validateEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.utils.Constant.PLEASE_ENTER_EMAIL
import com.example.utils.Constant.PLEASE_ENTER_PASSWORD
import com.example.utils.Constant.UPDATE_SUCCESSFULLY
import com.example.utils.Constant.USER_DELETED_SUCCESSFULLY
import com.example.utils.Constant.USER_ID_MISMATCHED
import com.example.utils.Constant.USER_NOT_FOUND
import com.example.utils.Constant.USER_REGISTER_SUCCESSFULLY
import com.example.utils.Constant.ZERO
import io.ktor.server.resources.*
import io.ktor.server.routing.post
import io.ktor.server.routing.put

fun Application.userRoute(
    component: KoinComponent
) {

    routing {

        get("/") {
            call.respondText("Testing End Points")
        }

        post("/register") {

            val userData: User = call.receive<User>()
            val email = userData.email ?: return@post errorResponse(
                message = PLEASE_ENTER_EMAIL
            )
            val password = userData.password ?: return@post errorResponse(
                message = PLEASE_ENTER_PASSWORD
            )
            try {
                if (email.isNotEmpty()) {
                    if (!validateEmail(email))
                        errorResponse(message = EMAIL_IS_NOT_VALID)
                    else if (component.userRepository.getAllEmail().contains(email))
                        errorResponse(message = EMAIL_ALREADY_EXITS)
                    else if (password.isEmpty())
                        errorResponse(message = PASSWORD_SHOULD_NOT_EMPTY)
                    else {
                        val hashPassword = hash(password)
                        val response = component.userRepository.registerUser(
                            User(email = email, password = hashPassword)
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
                                    USER_REGISTER_SUCCESSFULLY
                                )
                            )
                        )
                    }
                } else {
                    errorResponse(message = EMAIL_SHOULD_NOT_EMPTY)
                }
            } catch (e: Exception) {
                errorResponse()
            }
        }

        post("/login") {
            val user = call.receive<User>()

            val email = user.email ?: return@post errorResponse(
                message = PLEASE_ENTER_EMAIL
            )
            val password = user.password ?: return@post errorResponse(
                message = PLEASE_ENTER_PASSWORD
            )
            try {
                if (email.isNotEmpty()) {
                    if (!validateEmail(email))
                        errorResponse(message = EMAIL_IS_NOT_VALID)
                    else if (password.isEmpty())
                        errorResponse(message = PASSWORD_SHOULD_NOT_EMPTY)
                    else {
                        val response = component.userRepository.loginUser(user)
                        val hashPassword = hash(password)
                        if (response != null && response.password == hashPassword) {
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
                                        LOGGED_SUCCESSFULLY
                                    )
                                )
                            )
                        } else {
                            errorResponse(
                                statusCode = 400,
                                EMAIL_PASSWORD_DOES_NOT_MATCHED
                            )
                        }
                    }
                } else {
                    errorResponse(message = EMAIL_SHOULD_NOT_EMPTY)
                }
            } catch (e: java.lang.Exception) {
                errorResponse()
            }
        }
        delete<UserRoute.Id> {
            try {
                val userId = call.parameters["id"] ?: ZERO
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
                                USER_DELETED_SUCCESSFULLY
                            )
                        )
                    )
                else
                    errorResponse(
                        400,
                        USER_NOT_FOUND
                    )
            } catch (e: java.lang.Exception) {
                errorResponse()
            }
        }

        put<UserRoute.Id> {
            val user = call.receive<User>()
            val id = call.parameters["id"]
            val email = user.email ?: return@put errorResponse(
                message = PLEASE_ENTER_EMAIL
            )
            val password = user.password ?: return@put errorResponse(
                message = PLEASE_ENTER_PASSWORD
            )
            try {
                if (email.isEmpty())
                    errorResponse(message = EMAIL_SHOULD_NOT_EMPTY)
                else if (!validateEmail(email))
                    errorResponse(message = EMAIL_IS_NOT_VALID)
                else if (password.isEmpty())
                    errorResponse(message = PASSWORD_SHOULD_NOT_EMPTY)
                else {
                    val hashPassword = hash(user.password)
                    val result = component.userRepository.updateUser(id ?: ZERO, user.email, hashPassword)
                    if (result > 0) {
                        call.respond(
                            status = HttpStatusCode.OK, ApiResponse(
                                null,
                                listOf(user),
                                null,
                                MessageResponse(
                                    200,
                                    UPDATE_SUCCESSFULLY
                                )
                            )
                        )
                    } else if (component.userRepository.getEmailById(id ?: ZERO) == email) {
                        errorResponse(
                            message = EMAIL_DID_NOT_CHANGED
                        )
                    } else {
                        errorResponse(
                            message = USER_ID_MISMATCHED
                        )
                    }
                }

            } catch (e: java.lang.Exception) {
                errorResponse()
            }
        }

    }


}