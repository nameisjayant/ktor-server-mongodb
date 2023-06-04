package com.example.db

import com.example.features.user.domain.model.User
import com.example.utils.Constant
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.net.URLEncoder

class DatabaseConnection {
    private val username = URLEncoder.encode(System.getenv(Constant.USERNAME), "UTF-8")
    private val password = URLEncoder.encode(System.getenv(Constant.PASSWORD), "UTF-8")

    private val client =
        KMongo.createClient("mongodb://$username:$password@containers-us-west-68.railway.app:8002").coroutine
    private val database = client.getDatabase(Constant.DATABASE_NAME)
    private val userCollection: CoroutineCollection<User> = database.getCollection()

    suspend fun addUser(users: User): User {
        userCollection.insertOne(users)
        return users
    }

    suspend fun getAllUsers(): List<User> = userCollection.find().toList()

}