package com.hoant.taipeitour.view.attractions

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hoant.taipeitour.BR
import com.hoant.taipeitour.MainActivity
import com.hoant.taipeitour.R
import com.hoant.taipeitour.base.BaseFragment
import com.hoant.taipeitour.base.BaseRepository
import com.hoant.taipeitour.base.ViewModelProviderFactory
import com.hoant.taipeitour.databinding.FragmentAttractionDetailBinding
import com.hoant.taipeitour.repository.api.ApiClient
import com.hoant.taipeitour.repository.model.Attraction
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.util.Constants
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
        return ViewModelProvider(requireActivity()).get(AttractionViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setData()
        setListener()
    }

    private fun setData() {
        viewModel.attraction.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(Constants.ATTRACTION_DATA_KEY, Attraction::class.java)
        } else {
            arguments?.getParcelable(Constants.ATTRACTION_DATA_KEY) as? Attraction
        }

        viewModel.attraction.value?.let {
            Utils.textHtmlFormat(viewDataBinding.tvIntroduction, it.introduction)
            Utils.textSpannable(viewDataBinding.tvOfficialSite, it.officialSite)
            configToolbar(it.name)
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
        val bundle = Bundle().apply {
            putString(WebviewFragment.KEY_DATA_URL, url)
        }
        findNavController().navigate(R.id.action_AttractionDetail_to_Webview, bundle)
    }

    private fun configToolbar(title: String) {
        if (activity is MainActivity) {
            (activity as MainActivity).configToolbar(title)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_switch_language)?.apply {
            isVisible = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}