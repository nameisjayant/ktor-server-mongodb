package com.example.utils

import com.example.features.notes.domain.model.Note
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*


suspend fun PipelineContext<Unit, ApplicationCall>.errorResponse(
    statusCode: Int = 400,
    message: String = "Something Went Wrong",
    status:HttpStatusCode = HttpStatusCode.BadRequest
) {

    call.respond(
        status,
        ApiResponse<Note>(
            null,
            null,
            MessageResponse(
                statusCode, message
            )
        )
    )

}