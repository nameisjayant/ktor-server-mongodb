package com.example.data.repositories

import com.example.db.DatabaseConnection
import com.example.features.user.domain.model.User
import com.example.features.user.domain.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override suspend fun addUser(user: User): User {
        DatabaseConnection.userCollection.insertOne(user)
        return user
    }

}