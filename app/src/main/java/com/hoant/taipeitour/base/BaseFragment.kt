package com.hoant.taipeitour.base

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment < VM :ViewModel, B: ViewDataBinding, R : BaseRepository> : Fragment(){
    protected lateinit var viewDataBinding: B
    protected lateinit var viewModel: VM

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = createViewDataBinding(inflater, container, savedInstanceState)
        viewModel = createViewModel(requireActivity().application)

        variableId?.let {
            viewDataBinding.setVariable(it, viewModel)
            viewDataBinding.executePendingBindings()
        }

        return viewDataBinding.root
    }

    abstract var variableId: Int?

    abstract fun createViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): B

    abstract fun createRepository() : R

    abstract fun createViewModel(app: Application): VM

    override fun onDestroyView() {
        super.onDestroyView()
    }
}