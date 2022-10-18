package com.carbon.moove

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MooveApp : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}