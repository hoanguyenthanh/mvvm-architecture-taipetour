package com.hoant.taipeitour.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

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

    abstract fun createViewDataBinding(): B

    abstract fun createRepository() : R

    abstract fun createViewModel(): VM
}