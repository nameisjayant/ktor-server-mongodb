package com.example.features.notes.domain.route

import com.example.KoinComponent
import com.example.features.notes.domain.model.Note
import com.example.utils.ApiResponse
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
                            if (userId != null) {
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
                                            "Note Added Successfully"
                                        )
                                    )
                                )
                            } else {
                                errorResponse(
                                    statusCode = 401,
                                    "Invalid Token"
                                )
                            }


                        } catch (e: Exception) {
                            e.message
                            errorResponse()
                        }

                    } else {
                        errorResponse(
                            statusCode = 401,
                            "Please Pass Token In The Header"
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
                        val response = component.noteRepository.deleteNote(id ?: "0")
                        if (response > 0)
                            errorResponse(
                                statusCode = 200,
                                "Note Deleted Successfully",
                                HttpStatusCode.OK
                            )
                        else
                            errorResponse(
                                statusCode = 400,
                                "Note Id Not Found"
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
                            note, id ?: "0"
                        )
                        if (updated > 0)
                            errorResponse(
                                statusCode = 200,
                                "Note Updated Successfully",
                                HttpStatusCode.OK
                            )
                        else
                            errorResponse(
                                statusCode = 400,
                                "Note Id Not Found"
                            )


                    } catch (e: Exception) {
                        errorResponse()
                    }

                }

            }

        }
    }

}