package com.example.features.notes.domain.route

import com.example.KoinComponent
import com.example.features.notes.domain.model.Note
import com.example.utils.ApiResponse
import com.example.utils.Constant.INVALID_TOKEN
import com.example.utils.Constant.NOTE_ADDED_SUCCESSFULLY
import com.example.utils.Constant.NOTE_DELETED_SUCCESSFULLY
import com.example.utils.Constant.NOTE_ID_NOT_FOUND
import com.example.utils.Constant.NOTE_UPDATE_SUCCESSFULLY
import com.example.utils.Constant.PASS_TOKEN_IN_THE_HEADER
import com.example.utils.Constant.ZERO
import com.example.utils.MessageResponse
import com.example.utils.errorResponse
import com.example.utils.findUserByFromToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Exception


fun Application.noteRoute(
    component: KoinComponent
) {

    routing {
        route("/note") {
            authenticate("jwt") {
                post {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal != null) {
                        try {
                            val userId = principal.payload.subject
                            if (userId.isNotEmpty()) {
                                val note = call.receive<Note>()
                                component.noteRepository.addNote(
                                    note = Note(
                                        userId = userId,
                                        note = note.note,
                                        description = note.description
                                    )
                                )
                                call.respond(
                                    status = HttpStatusCode.Created,
                                    ApiResponse(
                                        null,
                                        listOf(note),
                                        null,
                                        MessageResponse(
                                            statusCode = 201,
                                            NOTE_ADDED_SUCCESSFULLY
                                        )
                                    )
                                )
                            } else {
                                errorResponse(
                                    statusCode = 401,
                                    INVALID_TOKEN
                                )
                            }


                        } catch (e: Exception) {
                            e.message
                            errorResponse()
                        }

                    } else {
                        errorResponse(
                            statusCode = 401,
                            PASS_TOKEN_IN_THE_HEADER
                        )
                    }

                }
                get {
                    try {
                        val principal = call.principal<JWTPrincipal>()
                        val userId = principal?.payload?.subject
                        if (userId != null) {
                            val response = component.noteRepository.getNotes(userId)
                            call.respond(
                                status = HttpStatusCode.OK,
                                ApiResponse(
                                    null,
                                    response,
                                    null,
                                    null
                                )
                            )
                        }
                    } catch (e: Exception) {
                        errorResponse()
                    }
                }
                delete("/{id}") {
                    try {
                        val id = call.parameters["id"]
                        val response = component.noteRepository.deleteNote(id ?: ZERO)
                        if (response > 0)
                            errorResponse(
                                statusCode = 200,
                                NOTE_DELETED_SUCCESSFULLY,
                                HttpStatusCode.OK
                            )
                        else
                            errorResponse(
                                message = NOTE_ID_NOT_FOUND
                            )
                    } catch (e: Exception) {
                        errorResponse()
                    }

                }
                put("/{id}") {
                    try {
                        val id = call.parameters["id"]
                        val note = call.receive<Note>()
                        val updated = component.noteRepository.updateNote(
                            note, id ?: ZERO
                        )
                        if (updated > 0)
                            errorResponse(
                                statusCode = 200,
                                NOTE_UPDATE_SUCCESSFULLY,
                                HttpStatusCode.OK
                            )
                        else
                            errorResponse(
                                message = NOTE_ID_NOT_FOUND
                            )


                    } catch (e: Exception) {
                        errorResponse()
                    }

                }

            }

        }
    }

}