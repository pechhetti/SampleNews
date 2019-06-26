package com.example.samplenews.di.modules

import android.content.Context
import com.example.samplenews.App
import com.example.samplenews.di.scopes.ApplicationContextQualifier
import com.example.samplenews.di.scopes.CustomApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {

    @Provides
    @CustomApplicationScope
    @ApplicationContextQualifier
    fun provideContext(): Context {
        return app.applicationContext
    }
}