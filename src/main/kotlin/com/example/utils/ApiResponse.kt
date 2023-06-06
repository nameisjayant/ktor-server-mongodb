package com.example.utils

import io.ktor.http.*


data class ApiResponse<T>(
    val statusCode: HttpStatusCode,
    val token: String? = null,
    val data: List<T>? = null
)