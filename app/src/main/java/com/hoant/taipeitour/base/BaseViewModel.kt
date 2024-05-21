package com.hoant.taipeitour.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.hoant.taipeitour.MyApplication

open class BaseViewModel(app: Application): AndroidViewModel(app) {
    fun getStringRes(resId: Int) = getApplication<Application>().getString(resId)
}