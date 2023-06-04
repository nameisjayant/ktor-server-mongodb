package com.example.data.repositories

import com.example.features.user.domain.model.User
import com.example.features.user.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override suspend fun addUser(user: User): User {
        TODO("Not yet implemented")
    }

}