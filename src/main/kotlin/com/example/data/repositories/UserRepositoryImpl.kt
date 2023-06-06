package com.example.data.repositories

import com.example.db.DatabaseConnection
import com.example.features.user.domain.model.User
import com.example.features.user.domain.repository.UserRepository
import org.litote.kmongo.eq

class UserRepositoryImpl : UserRepository {

    override suspend fun addUser(user: User): User {
        DatabaseConnection.userCollection.insertOne(user)
        return user
    }

    override suspend fun deleteUser(id: String): Boolean =
        DatabaseConnection.userCollection.deleteOne(User::id eq id).wasAcknowledged()

}