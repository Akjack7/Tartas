package com.example.cakeslist.core

import android.app.Application
import com.example.cakeslist.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    dispatcherFactoryModule,
                    netModule,
                    viewModelModule,
                    useCasesModule,
                    cakeApiModule,
                    repositoryModule
                )
            )
        }
    }
}