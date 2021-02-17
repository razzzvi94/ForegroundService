package com.example.foregroundservice.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foregroundservice.data.entities.Timer
import io.reactivex.Flowable


@Dao
interface TimerDao {
    @Query("SELECT * FROM timer")
    fun getTimer(): Flowable<Timer>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTimer(timer: Timer)

    @Query("DELETE FROM timer")
    fun deleteTimer()
}