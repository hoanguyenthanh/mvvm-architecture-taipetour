package com.hoant.taipeitour.base

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.hoant.taipeitour.MyApplication
import com.hoant.taipeitour.util.Constants
import com.hoant.taipeitour.util.SharedPref
import java.util.Locale


abstract class BaseActivity < VM: BaseViewModel, B : ViewDataBinding, R : BaseRepository> : AppCompatActivity(){
    protected lateinit var viewDataBinding: B
    protected lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
        viewDataBinding = createViewDataBinding()
        viewDataBinding.lifecycleOwner = this

        variableId?.let {
            viewDataBinding.setVariable(it, viewModel)
            viewDataBinding.executePendingBindings()
        }
    }

    abstract var variableId: Int?

    abstract fun createViewModel(): VM

    abstract fun createViewDataBinding(): B

    abstract fun createRepository() : R


    protected fun setLocaleLocal(language: String) {
        MyApplication.language = language
        SharedPref(this).putString(Constants.LANGUAGE_DATA_KEY, language)
        restartActivity()
    }

    private fun restartActivity() {
        finish()
        startActivity(intent)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(createContextLocale(newBase))
    }

    private fun createContextLocale(context: Context): Context {
        val locale = Locale(MyApplication.language)
        val config = context.resources.configuration

        config.setLocale(locale)
        Locale.setDefault(locale)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, resources.displayMetrics)
        }
        return context
    }
}