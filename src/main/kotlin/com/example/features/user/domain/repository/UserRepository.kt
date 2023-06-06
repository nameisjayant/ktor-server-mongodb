package com.example.features.user.domain.repository

import com.example.db.DatabaseConnection
import com.example.features.user.domain.model.User

interface UserRepository {

    suspend fun addUser(user: User): User

    suspend fun deleteUser(id: String): Boolean

    suspend fun updateUser(
        id: String,
        email: String,
        password: String
    ): User

    suspend fun loginUser(user: User): User


}