package com.example.db

import com.example.features.notes.domain.model.Note
import com.example.features.user.domain.model.User
import com.example.utils.Constant
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.net.URL
import java.net.URLEncoder

object DatabaseConnection {
        private val username = URLEncoder.encode(System.getenv(Constant.USERNAME), "UTF-8")
         private val password = URLEncoder.encode(System.getenv(Constant.PASSWORD), "UTF-8")
        private val url = "mongodb://$username:$password@containers-us-west-68.railway.app:8002"

//    private val username = URLEncoder.encode("nameisjayant", "UTF-8")
//    private val password = URLEncoder.encode("@@jks123@@", "UTF-8")

//    private val url = "mongodb+srv://$username:$password@cluster0.f8fcv5s.mongodb.net/"

    private val client =
        KMongo.createClient(url).coroutine
    private val database = client.getDatabase(Constant.DATABASE_NAME)
    val userCollection: CoroutineCollection<User> = database.getCollection()
    val noteCollection: CoroutineCollection<Note> = database.getCollection()

}