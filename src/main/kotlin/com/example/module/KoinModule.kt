package com.example.module

import com.example.db.DatabaseConnection
import org.koin.dsl.module


val koinModule = module {

    single { DatabaseConnection() }

}