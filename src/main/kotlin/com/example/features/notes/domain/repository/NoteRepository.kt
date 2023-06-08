package com.example.features.notes.domain.repository

import com.example.features.notes.domain.model.Note

interface NoteRepository {

    suspend fun addNote(note: Note): Note
    suspend fun deleteNote(id: String): Long
    suspend fun getNotes(userId: String): List<Note>
    suspend fun updateNote(note: Note): Long

}