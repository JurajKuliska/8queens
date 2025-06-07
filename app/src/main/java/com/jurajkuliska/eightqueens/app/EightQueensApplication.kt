package com.jurajkuliska.eightqueens.app

import android.app.Application
import com.jurajkuliska.eightqueens.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class EightQueensApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@EightQueensApplication)
            modules(appModule)
        }
    }
}