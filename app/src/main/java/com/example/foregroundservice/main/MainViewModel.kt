package com.example.foregroundservice.main

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foregroundservice.Util.disposeBy
import com.example.foregroundservice.data.repository.TimerRepository
import com.example.foregroundservice.data.rx.AppRxSchedulers
import com.example.foregroundservice.main.service.ForegroundService
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val context: Context,
    private val timerRepository: TimerRepository,
    private val compositeDisposable: CompositeDisposable,
    private val rxSchedulers: AppRxSchedulers
) : ViewModel() {
    val counter: MutableLiveData<Int> = MutableLiveData<Int>().apply { value = 0 }
    val buttonText: MutableLiveData<String> = MutableLiveData<String>().apply { value = "START" }

    fun buttonClick() {
        when (buttonText.value) {
            "START" -> {
                startService(context, "Foreground Service is counting...")
                buttonText.value = "STOP"
            }
            "STOP" -> {
                stopService(context)
                buttonText.value = "START"
                counter.value = 0
            }
        }
    }

    private fun startService(context: Context, message: String) {
        val startIntent = Intent(context, ForegroundService::class.java)
        startIntent.putExtra("inputExtra", message)
        ContextCompat.startForegroundService(context, startIntent)

        ForegroundService.startLongBackgroundProcess(timerRepository, rxSchedulers, compositeDisposable)
    }

    private fun stopService(context: Context) {
        val stopIntent = Intent(context, ForegroundService::class.java)
        context.stopService(stopIntent)

        timerRepository.deleteTimer()
        ForegroundService.myHandler.removeCallbacks(ForegroundService.myRunnable)
    }

    private fun handleTimer(time: Int){
        if(time <= 30){
            counter.value = time
        }
        else{
            stopService(context)
            buttonText.value = "START"
            counter.value = 0
        }
    }

    fun observeTimerChange(){
        timerRepository.getTimer()
            .subscribeOn(rxSchedulers.background())
            .observeOn(rxSchedulers.androidUI())
            .subscribe({
                handleTimer(it.time)
            },{
                it.localizedMessage
            })
            .disposeBy(compositeDisposable)
    }
}