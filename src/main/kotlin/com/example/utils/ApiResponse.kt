package com.example.utils

import io.ktor.http.*


data class ApiResponse<T>(
    val status: Boolean,
    val statusCode: HttpStatusCode,
    val message: String,
    val token: String? = null,
    val data: List<T>? = null
)