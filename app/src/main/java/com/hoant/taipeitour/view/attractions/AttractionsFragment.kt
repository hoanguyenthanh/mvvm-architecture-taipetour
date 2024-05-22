package com.hoant.taipeitour.view.attractions

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseAdapter
import com.hoant.taipeitour.base.BaseFragment
import com.hoant.taipeitour.databinding.FragmentAttractionBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.repository.model.Resource
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.util.Constants
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
        return ViewModelProvider(requireActivity()).get(AttractionViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObserves()
        init()
    }


    override fun onStart() {
        super.onStart()
        viewDataBinding.rcvAttractions.addOnScrollListener(onScrollListener)
    }

    override fun onPause() {
        super.onPause()
        viewDataBinding.rcvAttractions.removeOnScrollListener(onScrollListener)
    }

    private fun init() {
        attractionsAdapter = AttractionsAdapter(this)
        viewDataBinding.rcvAttractions.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = attractionsAdapter
        }
    }

    private val onScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                val lastPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                val total = viewModel.attractionsResponse.value?.result?.total ?: 0
//                    if (lastPosition * page < total) {
//                        page++
//                    }
            }
        }
    }

    private fun initObserves() {
        viewModel.languageSelected.observe(viewLifecycleOwner) {
            if (it?.isEmpty() == false) {
                viewModel.getAttractions(it, page)
            }
        }

        viewModel.attractionsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.result?.data?.let { data ->
                        attractionsAdapter.differ.submitList(data)
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
        viewDataBinding.progressbar.visibility = View.GONE
    }

    private fun showProgressBar() {
        viewDataBinding.progressbar.visibility = View.VISIBLE
    }

    override fun onItemCLicked(item: Attraction) {
        val bundle = Bundle().apply {
            putParcelable(Constants.ATTRACTION_DATA_KEY, item)
        }
        findNavController().navigate(R.id.action_Attractions_to_AttractionDetail, bundle)
    }
}