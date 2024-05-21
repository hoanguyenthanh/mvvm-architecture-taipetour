package com.hoant.taipeitour.view.attractions

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hoant.taipeitour.MyApplication
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseAdapter
import com.hoant.taipeitour.base.BaseFragment
import com.hoant.taipeitour.base.ViewModelProviderFactory
import com.hoant.taipeitour.databinding.FragmentAttractionBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.repository.model.Resource
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.util.errorSnack
import com.hoant.taipeitour.viewmodel.attraction.AttractionViewModel

class AttractionsFragment : BaseFragment<AttractionViewModel, FragmentAttractionBinding, AttractionRepository>(),
    BaseAdapter.OnItemClick<Attraction> {

    private lateinit var attractionsAdapter: AttractionsAdapter
    override var variableId: Int? = null
    private var page = 1


    override fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAttractionBinding {
        return FragmentAttractionBinding.inflate(inflater, container, false)
    }

    override fun createRepository(): AttractionRepository {
        return AttractionRepository(ApiClient.attractionApi)
    }

    override fun createViewModel(app: Application): AttractionViewModel {
        val factory = ViewModelProviderFactory(app, createRepository())
        return ViewModelProvider(viewModelStore, factory).get(AttractionViewModel::class.java)
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserves()
        init()
    }

    private fun init() {
        attractionsAdapter = AttractionsAdapter(this)
        viewDataBinding.rcvAttractions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = attractionsAdapter
        }

        viewModel.getAttractions("vi", page)
    }

    private fun initObserves() {
        viewModel.attractionsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.result?.data?.let { data ->
                        attractionsAdapter.differ.submitList(data)
                        viewDataBinding.rcvAttractions.adapter = attractionsAdapter
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        viewDataBinding.rootLayout.errorSnack(it, Snackbar.LENGTH_LONG)
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        viewDataBinding.progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        viewDataBinding.progress.visibility = View.VISIBLE
    }

    override fun onItemCLicked(item: Attraction) {
        val bundle = Bundle().apply {
            putParcelable("attraction", item)
        }
        findNavController().navigate(R.id.action_Attractions_to_AttractionDetail, bundle)
    }
}