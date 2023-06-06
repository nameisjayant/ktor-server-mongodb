package com.example.module

import com.example.data.repositories.NoteRepositoryImpl
import com.example.data.repositories.UserRepositoryImpl
import com.example.features.notes.domain.repository.NoteRepository
import com.example.features.user.domain.repository.UserRepository
import org.koin.dsl.bind
import org.koin.dsl.module


val koinModule = module {

    factory { UserRepositoryImpl() } bind UserRepository::class
    factory { NoteRepositoryImpl() } bind NoteRepository::class

}