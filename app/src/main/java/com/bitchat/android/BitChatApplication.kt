package com.bitchat.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BitChatApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
    }
}
