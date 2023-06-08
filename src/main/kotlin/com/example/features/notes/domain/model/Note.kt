package com.example.features.notes.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Note(
    @BsonId
    val id: String = ObjectId().toString(),
    val userId: String,
    val note: String,
    val description: String
)
