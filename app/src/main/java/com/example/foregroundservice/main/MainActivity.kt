package com.example.foregroundservice.main

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.foregroundservice.R
import com.example.foregroundservice.databinding.ActivityMainBinding
import com.example.foregroundservice.main.service.ForegroundService
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
            .apply {
                viewModel = this@MainActivity.mainViewModel
                lifecycleOwner = this@MainActivity
            }

        if (isMyServiceRunning(ForegroundService::class.java)) {
            mainViewModel.buttonText.value = "STOP"
        }

        mainViewModel.observeTimerChange()
    }

    private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}