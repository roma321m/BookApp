package com.roman.bookapp

import android.app.Application
import com.roman.bookapp.di.initKoin
import org.koin.android.ext.koin.androidContext

class BookApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@BookApp)
        }
    }
}