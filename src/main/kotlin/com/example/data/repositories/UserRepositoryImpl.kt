package com.example.data.repositories

import com.example.db.DatabaseConnection
import com.example.features.user.domain.model.User
import com.example.features.user.domain.repository.UserRepository
import org.bson.Document

class UserRepositoryImpl : UserRepository {

    override suspend fun registerUser(user: User): User {
        DatabaseConnection.userCollection.insertOne(user)
        return user
    }

    override suspend fun deleteUser(id: String): Long {
        val query = Document("id", id)
        return DatabaseConnection.userCollection.deleteOne(query).deletedCount
    }

    override suspend fun updateUser(id: String, email: String, password: String): Long {
        val query = Document("_id", id)
        val update = Document(
            "\$set",
            Document("email", email)
                .append("password", password)
        )
        val result = DatabaseConnection.userCollection.updateOne(query, update)
        return result.modifiedCount
    }

    override suspend fun loginUser(user: User): User? {
        val query = Document("email", user.email)
        return DatabaseConnection.userCollection.find(query).first()
    }

    override suspend fun getAllEmail(): List<String> {
        return DatabaseConnection.userCollection.find().toList().map { it.email }
    }

    override suspend fun getEmailById(id: String): String {
        val query = Document("_id", id)
        return DatabaseConnection.userCollection.find(query).first()?.email ?: ""
    }

}