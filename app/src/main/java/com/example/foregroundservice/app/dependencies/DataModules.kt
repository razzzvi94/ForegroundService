package com.example.foregroundservice.app.dependencies

import com.example.foregroundservice.data.RoomDB
import com.example.foregroundservice.data.repository.TimerRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val databaseModule: Module = module {
    single { RoomDB(androidContext()).appDatabase }
    single { TimerRepository(get()) }
}