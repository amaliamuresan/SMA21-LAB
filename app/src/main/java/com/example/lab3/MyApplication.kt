package com.example.lab3

import android.app.Application
import android.graphics.Bitmap

class MyApplication : Application() {
    private var bitmap: Bitmap? = null

    fun setBitmap(value : Bitmap?) {
        bitmap = value
    }

    fun getBitmap() : Bitmap? = bitmap

    override fun onCreate() {
        super.onCreate()
        singleton = this
    }

    companion object {
        private var singleton = MyApplication()
        fun getInstance() = singleton
    }
}