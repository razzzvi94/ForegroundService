package com.example.foregroundservice.app.dependencies

import com.example.foregroundservice.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val viewModelsModule: Module = module {
    viewModel { MainViewModel(get(), get(), get(), get()) }
}