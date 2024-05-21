package com.hoant.taipeitour.view.attractions

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hoant.taipeitour.BR
import com.hoant.taipeitour.MyApplication
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseFragment
import com.hoant.taipeitour.base.BaseRepository
import com.hoant.taipeitour.base.ViewModelProviderFactory
import com.hoant.taipeitour.databinding.FragmentAttractionDetailBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.util.Utils
import com.hoant.taipeitour.view.webview.WebviewFragment
import com.hoant.taipeitour.viewmodel.attraction.AttractionViewModel

class AttractionDetailFragment : BaseFragment<AttractionViewModel, FragmentAttractionDetailBinding, BaseRepository>() {

    override var variableId: Int? = BR.viewModel

    override fun createViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAttractionDetailBinding {
        return FragmentAttractionDetailBinding.inflate(inflater, container, false)
    }

    override fun createRepository(): BaseRepository {
        return AttractionRepository(ApiClient.attractionApi)
    }

    override fun createViewModel(app: Application): AttractionViewModel {
        val factory = ViewModelProviderFactory(app, createRepository())
        return ViewModelProvider(viewModelStore, factory).get(AttractionViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setData()
        setListener()

    }

    private fun setData() {
        viewModel.attraction.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("attraction", Attraction::class.java)
        } else {
            arguments?.getParcelable("attraction") as? Attraction
        }

        viewModel.attraction.value?.let {
            Utils.textHtmlFormat(viewDataBinding.tvIntroduction, it.introduction)
            Utils.textSpannable(viewDataBinding.tvOfficialSite, it.officialSite)
        }
    }

    private fun setListener() {
        viewDataBinding.tvOfficialSite.setOnClickListener {
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
        activity?.supportFragmentManager?.run {
            beginTransaction()
                .add(R.id.fragmentContent, webViewFragment, WebviewFragment::class.simpleName)
                .addToBackStack(null)
                .commit()
        }
    }
}