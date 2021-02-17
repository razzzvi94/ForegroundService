package com.example.foregroundservice.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class RoomDB(context: Context) {
    val appDatabase: AppDatabase

    init {
        appDatabase = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
            })
            .allowMainThreadQueries()
            .build()
    }

    companion object {
        private const val DATABASE_NAME = "service-database"
    }
}