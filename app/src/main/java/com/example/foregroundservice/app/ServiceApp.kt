package com.example.foregroundservice.app

import android.app.Application
import com.example.foregroundservice.BuildConfig
import com.example.foregroundservice.app.dependencies.databaseModule
import com.example.foregroundservice.app.dependencies.rxModules
import com.example.foregroundservice.app.dependencies.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

class ServiceApp: Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@ServiceApp)
            modules(
                listOf(
                    viewModelsModule,
                    databaseModule,
                    rxModules
                )
            )
        }
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}