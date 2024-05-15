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
    protected lateinit var binding : B
    protected lateinit var viewModel: VM


    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getFragmentBinding(inflater,container) as B

        val factory = ViewModelProviderFactory(MyApplication.instance, getFragmentRepository())
        viewModel = ViewModelProvider(this,factory).get(getViewModel())

        return binding.root
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater,container: ViewGroup?) : ViewDataBinding

    abstract fun getFragmentRepository() : R

    abstract fun getViewModel() : Class<VM>

}