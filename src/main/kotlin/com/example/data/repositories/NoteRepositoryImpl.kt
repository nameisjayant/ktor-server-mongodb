package com.example.data.repositories

import com.example.db.DatabaseConnection
import com.example.features.notes.domain.model.Note
import com.example.features.notes.domain.repository.NoteRepository
import com.example.features.user.domain.model.User
import org.bson.Document
import org.litote.kmongo.util.idValue
import javax.xml.crypto.Data

class NoteRepositoryImpl : NoteRepository {

    override suspend fun addNote(note: Note): Note {
        DatabaseConnection.noteCollection.insertOne(note)
        return note
    }

    override suspend fun deleteNote(id: String): Long {
        val query = Document("_id", id)
        return DatabaseConnection.noteCollection.deleteOne(query).deletedCount
    }

    override suspend fun getNotes(userId: String): List<Note> {
        val query = Document("userId", userId)
        return DatabaseConnection.noteCollection.find(query).toList()
    }

    override suspend fun updateNote(note: Note): Long {
        val query = Document("_id", note.id)
        val update = Document(
            "\$set",
            Document("note", note.note)
                .append("description", note.description)
        )
        val result = DatabaseConnection.noteCollection.updateOne(query, update)
        return result.modifiedCount
    }
}