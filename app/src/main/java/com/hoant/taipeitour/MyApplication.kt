package com.hoant.taipeitour

import android.app.Application
import com.hoant.taipeitour.util.Constants
import com.hoant.taipeitour.util.SharedPref

class MyApplication : Application() {
    companion object {
        var language: String = "en"
    }

    override fun onCreate() {
        super.onCreate()
        val languageCached = SharedPref(this).getString(Constants.LANGUAGE_DATA_KEY)
        languageCached?.let {
            language = it
        }
    }
}