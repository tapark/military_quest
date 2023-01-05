package com.tapark.military_quest

import android.app.Application
import com.tapark.military_quest.utils.PrefManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        PrefManager.init(applicationContext)
    }
}