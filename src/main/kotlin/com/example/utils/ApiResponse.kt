package com.example.utils


data class ApiResponse<T>(
    val token: String? = null,
    val data: List<T>? = null,
    val error: MessageResponse? = null,
    val success: MessageResponse? = null
)

data class MessageResponse(
    val statusCode: Int,
    val message: String
)