package com.example.foregroundservice.main.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.foregroundservice.R
import com.example.foregroundservice.Util.disposeBy
import com.example.foregroundservice.data.entities.Timer
import com.example.foregroundservice.data.repository.TimerRepository
import com.example.foregroundservice.data.rx.AppRxSchedulers
import com.example.foregroundservice.main.MainActivity
import io.reactivex.disposables.CompositeDisposable


class ForegroundService : Service() {

    private val CHANNEL_ID = "ForegroundService Kotlin"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //do heavy work on a background thread
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Counter")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        val myHandler = Handler(Looper.getMainLooper())
        lateinit var myRunnable: Runnable

        private fun insertTimer(
            timer: Int,
            timerRepository: TimerRepository,
            rxSchedulers: AppRxSchedulers,
            compositeDisposable: CompositeDisposable
        ) {
            timerRepository.saveTimer(Timer(id = 0, timer))
                .subscribeOn(rxSchedulers.background())
                .observeOn(rxSchedulers.androidUI())
                .subscribe({

                }, {
                    it.localizedMessage
                })
                .disposeBy(compositeDisposable)
        }


        fun startLongBackgroundProcess(
            timerRepository: TimerRepository,
            rxSchedulers: AppRxSchedulers,
            compositeDisposable: CompositeDisposable
        ) {

            var count = 0
            myRunnable = Runnable {
                count++
                insertTimer(count, timerRepository, rxSchedulers, compositeDisposable)
                myHandler.postDelayed(myRunnable, 1000)
            }
            myHandler.postDelayed(myRunnable, 1000)
        }
    }
}