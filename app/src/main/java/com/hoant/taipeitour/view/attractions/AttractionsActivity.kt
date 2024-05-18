package com.hoant.taipeitour.view.attractions

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseActivity
import com.hoant.taipeitour.base.BaseAdapter
import com.hoant.taipeitour.databinding.ActivityAttractionBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.repository.model.Resource
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.util.errorSnack
import com.hoant.taipeitour.viewmodel.attraction.AttractionViewModel

class AttractionsActivity: BaseActivity<AttractionViewModel, ActivityAttractionBinding, AttractionRepository>(), BaseAdapter.OnItemClick<Attraction>{

    override var variableId: Int? = null

    override fun getViewBinding(): ActivityAttractionBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_attraction)
    }

    override fun getRepository(): AttractionRepository {
        return AttractionRepository(ApiClient.attractionApi)
    }

    override fun getViewModelClass(): Class<AttractionViewModel> {
        return AttractionViewModel::class.java
    }


    private lateinit var attractionsAdapter: AttractionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserves()
        init()
    }



    private fun init() {
        attractionsAdapter = AttractionsAdapter(this)
        binding.rcvAttractions.apply {
            layoutManager = LinearLayoutManager(this@AttractionsActivity)
            adapter = attractionsAdapter
        }

        viewModel.getAttractions("vi", mPage)
    }

    private fun initObserves() {
        viewModel.attractionsResponse.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.result?.data?.let { data ->
                        attractionsAdapter.differ.submitList(data)
                        binding.rcvAttractions.adapter = attractionsAdapter
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        binding.rootLayout.errorSnack(it, Snackbar.LENGTH_LONG)
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun onItemCLicked(item: Attraction) {
        val intent = Intent(this, AttractionDetailActivity::class.java).apply {
            putExtra("attraction", item)
        }
        startActivity(intent)
    }
}