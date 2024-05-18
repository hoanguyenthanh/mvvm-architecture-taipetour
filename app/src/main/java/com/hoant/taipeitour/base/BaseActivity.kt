package com.hoant.taipeitour.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseActivity < VM: BaseViewModel, B : ViewDataBinding, R : BaseRepository> : AppCompatActivity(){
    protected lateinit var binding : B
    protected lateinit var viewModel: VM
    protected var mPage = 1


    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelProviderFactory(application, getRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModelClass()]

        binding = getViewBinding()
        binding.lifecycleOwner = this
        variableId?.let {
            binding.setVariable(it, viewModel)
            binding.executePendingBindings()
        }
    }

    abstract var variableId: Int?

    abstract fun getViewBinding(): B

    abstract fun getRepository() : R

    abstract fun getViewModelClass() : Class<VM>
}