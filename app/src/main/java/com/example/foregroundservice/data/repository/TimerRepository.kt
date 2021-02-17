package com.example.foregroundservice.data.repository

import com.example.foregroundservice.data.AppDatabase
import com.example.foregroundservice.data.entities.Timer
import io.reactivex.Flowable

class TimerRepository(private val db: AppDatabase) {

    fun getTimer(): Flowable<Timer>{
        return db.timerDao().getTimer()
    }

    fun saveTimer(timer: Timer): Flowable<Any> {
        return Flowable.just(db.timerDao().saveTimer(timer))
    }

    fun deleteTimer(){
        return db.timerDao().deleteTimer()
    }
}