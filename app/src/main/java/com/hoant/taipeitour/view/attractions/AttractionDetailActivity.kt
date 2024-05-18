package com.hoant.taipeitour.view.attractions

import android.os.Build
import android.os.Bundle
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import com.hoant.taipeitour.BR
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseActivity
import com.hoant.taipeitour.base.BaseRepository
import com.hoant.taipeitour.databinding.ActivityAttractionDetailBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.util.Utils
import com.hoant.taipeitour.view.webview.WebviewFragment
import com.hoant.taipeitour.viewmodel.attraction.AttractionViewModel

class AttractionDetailActivity: BaseActivity<AttractionViewModel, ActivityAttractionDetailBinding, BaseRepository>() {
    override var variableId: Int? = BR.viewModel

    override fun getViewBinding(): ActivityAttractionDetailBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_attraction_detail)
    }

    override fun getRepository(): BaseRepository {
        return AttractionRepository(ApiClient.attractionApi)
    }

    override fun getViewModelClass(): Class<AttractionViewModel> {
        return AttractionViewModel::class.java
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setData()
        setToolbar()
        setListener()
    }

    private fun setData() {
        viewModel.attraction.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.extras?.getParcelable("attraction", Attraction::class.java)
        } else {
            intent?.extras?.getParcelable("attraction") as? Attraction
        }

        viewModel.attraction.value?.let {
            Utils.textHtmlFormat(binding.tvIntroduction, it.introduction)
            Utils.textSpannable(binding.tvOfficialSite, it.officialSite)
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.layoutToolbar.toolbar)
        binding.layoutToolbar.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }

    private fun setListener() {
        binding.tvOfficialSite.setOnClickListener {
            viewModel.attraction.value?.officialSite?.let {
                openOfficialSite(it)
            }
        }
    }

    private fun openOfficialSite(url: String) {
        val webViewFragment = WebviewFragment()
        webViewFragment.arguments = Bundle().apply {
            putString(WebviewFragment.KEY_DATA_URL, url)
        }
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContent, webViewFragment,  WebviewFragment::class.simpleName)
            .addToBackStack(null)
            .commit()
    }
}