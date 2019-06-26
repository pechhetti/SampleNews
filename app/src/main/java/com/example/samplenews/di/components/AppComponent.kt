package com.example.samplenews.di.components

import com.example.samplenews.App
import com.example.samplenews.di.modules.AppModule
import com.example.samplenews.di.modules.ServiceUtilModule
import com.example.samplenews.ui.news.NewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ServiceUtilModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(viewModel: NewsViewModel)
}