package com.example.data.repositories

import com.example.features.notes.domain.model.Note
import com.example.features.notes.domain.repository.NoteRepository

class NoteRepositoryImpl : NoteRepository {

    override suspend fun addNote(note: Note): Note {
        TODO("Not yet implemented")
    }
}