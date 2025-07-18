package com.example.myweather.app

import android.app.Application
import com.example.myweather.di.dataModule
import com.example.myweather.di.interactorModule
import com.example.myweather.di.repositoryModule
import com.example.myweather.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}