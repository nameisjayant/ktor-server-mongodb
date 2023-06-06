package com.example

import com.example.data.repositories.NoteRepositoryImpl
import com.example.data.repositories.UserRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinComponent : KoinComponent {

    val userRepository by inject<UserRepositoryImpl>()
    val noteRepository by inject<NoteRepositoryImpl>()

}