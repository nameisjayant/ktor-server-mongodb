package com.example.features.notes.domain.route

import com.example.KoinComponent
import com.example.features.notes.domain.model.Note
import com.example.utils.ApiResponse
import com.example.utils.MessageResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
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
                    try {
                        val note = call.receive<Note>()
                        component.noteRepository.addNote(note = note)
                        call.respond(
                            status = HttpStatusCode.Created,
                            ApiResponse<Note>(
                                null,
                                listOf(note),
                                null,
                                MessageResponse(
                                    statusCode = 201,
                                    "Note Added Successfully"
                                )
                            )
                        )

                    } catch (e: Exception) {
                        call.respond(
                            status = HttpStatusCode.Created,
                            ApiResponse<Note>(
                                null,
                                null,
                                MessageResponse(
                                    statusCode = 400,
                                    "Something Went Wrong"
                                )
                            )
                        )
                    }
                }
                get("/{id}") {
                    try {
                        val userId = call.parameters["id"]
                        val response = component.noteRepository.getNotes(userId ?: "0")
                        call.respond(
                            status = HttpStatusCode.OK,
                            ApiResponse<Note>(
                                null,
                                response,
                                null,
                                null
                            )
                        )

                    } catch (e: Exception) {
                        call.respond(
                            status = HttpStatusCode.Created,
                            ApiResponse<Note>(
                                null,
                                null,
                                MessageResponse(
                                    statusCode = 400,
                                    "Something Went Wrong"
                                )
                            )
                        )
                    }

                }
            }

        }
    }

}