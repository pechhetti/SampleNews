package com.example.samplenews

import android.app.Application
import com.example.samplenews.di.components.AppComponent
import com.example.samplenews.di.components.DaggerAppComponent
import com.example.samplenews.di.modules.AppModule

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        component.inject(this)
    }
}