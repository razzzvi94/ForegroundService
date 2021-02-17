package com.example.foregroundservice.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foregroundservice.data.dao.TimerDao
import com.example.foregroundservice.data.entities.Timer

@Database(
    entities = [Timer::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun timerDao(): TimerDao
}