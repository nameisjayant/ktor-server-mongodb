package com.example.data.repositories

import com.example.db.DatabaseConnection
import com.example.features.user.domain.model.User
import com.example.features.user.domain.repository.UserRepository
import org.bson.Document
import org.litote.kmongo.Data
import org.litote.kmongo.eq

class UserRepositoryImpl : UserRepository {

    override suspend fun addUser(user: User): User {
        DatabaseConnection.userCollection.insertOne(user)
        return user
    }

    override suspend fun deleteUser(id: String): Long {
        val query = Document("id", id)
        return DatabaseConnection.userCollection.deleteOne(query).deletedCount
    }

    override suspend fun updateUser(id: String, email: String, password: String): User {
        DatabaseConnection.userCollection.updateOneById(
            id = id,
            update = User(email = email, password = password)
        )
        return User(email = email, password = password)
    }

    override suspend fun loginUser(user: User): User? {
        val query = Document("email", user.email)
        return DatabaseConnection.userCollection.find(query).first()
    }

}