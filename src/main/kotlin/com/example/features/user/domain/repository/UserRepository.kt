package com.example.features.user.domain.repository

import com.example.features.user.domain.model.User

interface UserRepository {

    suspend fun addUser(user: User): User

    suspend fun deleteUser(id: String): Boolean


}