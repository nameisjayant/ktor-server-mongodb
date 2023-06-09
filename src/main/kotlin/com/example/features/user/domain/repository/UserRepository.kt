package com.example.features.user.domain.repository

import com.example.features.user.domain.model.User

interface UserRepository {

    suspend fun registerUser(user: User): User
    suspend fun deleteUser(id: String): Long
    suspend fun updateUser(
        id: String,
        email: String,
        password: String
    ): Long
    suspend fun loginUser(user: User): User?

    suspend fun getAllEmail():List<String>
    suspend fun getEmailById(id:String):String


}