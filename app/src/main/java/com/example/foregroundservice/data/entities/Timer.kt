package com.example.foregroundservice.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class Timer(
    @PrimaryKey(autoGenerate = false)
    var id: Long = 0,

    @ColumnInfo(name = "time")
    var time: Int = 0
)