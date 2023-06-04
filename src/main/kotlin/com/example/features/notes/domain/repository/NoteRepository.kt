package com.example.features.notes.domain.repository

import com.example.features.notes.domain.model.Note

interface NoteRepository {

    suspend fun addNote(note: Note): Note

}