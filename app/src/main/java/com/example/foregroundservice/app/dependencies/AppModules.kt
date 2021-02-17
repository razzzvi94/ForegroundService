package com.example.foregroundservice.app.dependencies

import io.reactivex.disposables.CompositeDisposable
import com.example.foregroundservice.data.rx.AppRxSchedulers
import org.koin.core.module.Module
import org.koin.dsl.module

val rxModules: Module = module {
    single { AppRxSchedulers() }
    factory { CompositeDisposable() }
}