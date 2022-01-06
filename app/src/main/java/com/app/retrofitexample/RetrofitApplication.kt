package com.app.retrofitexample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RetrofitApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}