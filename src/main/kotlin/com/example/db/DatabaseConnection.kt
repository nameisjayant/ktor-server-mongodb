package com.example.db

import com.example.model.User
import com.example.utils.Constant
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.net.URLEncoder

class DatabaseConnection {
    private val username = URLEncoder.encode("nameisjayant", "UTF-8")
    private val password = URLEncoder.encode("@@jks123@@", "UTF-8")

    private val client =
        KMongo.createClient("mongodb+srv://$username:$password@cluster0.f8fcv5s.mongodb.net/?retryWrites=true&w=majority").coroutine
    private val database = client.getDatabase(Constant.DATABASE_NAME)
    private val userCollection: CoroutineCollection<User> = database.getCollection()

    suspend fun addUser(users: User): User {
        userCollection.insertOne(users)
        return users
    }

    suspend fun getAllUsers(): List<User> = userCollection.find().toList()

}