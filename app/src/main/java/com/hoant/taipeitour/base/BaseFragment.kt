package com.hoant.taipeitour.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hoant.taipeitour.MyApplication

abstract class BaseFragment < VM :ViewModel, B : ViewDataBinding, R : BaseRepository> : Fragment(){
    protected lateinit var binding: B
    protected lateinit var viewModel: VM


    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getViewBinding(inflater, container) as B

        val factory = ViewModelProviderFactory(MyApplication.instance, getRepository())
        viewModel = ViewModelProvider(this,factory).get(getViewModelClass())

        return binding.root
    }

    abstract fun getViewBinding(inflater: LayoutInflater,container: ViewGroup?) : ViewDataBinding

    abstract fun getRepository() : R

    abstract fun getViewModelClass() : Class<VM>

    override fun onDestroyView() {
        super.onDestroyView()
    }
}