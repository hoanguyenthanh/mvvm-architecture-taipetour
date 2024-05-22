package com.hoant.taipeitour

import android.app.Application

class MyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
        var language: String = "en"
    }
}