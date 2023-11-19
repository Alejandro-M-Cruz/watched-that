package com.example.watchedthat

import android.app.Application
import com.example.watchedthat.data.AppContainer
import com.example.watchedthat.data.DefaultAppContainer

class WatchedThatApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}