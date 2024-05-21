package com.hoant.taipeitour.view.webview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.hoant.taipeitour.MainActivity
import com.hoant.taipeitour.R
import com.hoant.taipeitour.databinding.FragmentWebviewBinding

class WebviewFragment: Fragment() {
    companion object {
        val KEY_DATA_URL = "key_data_url"
    }

    private var viewBinding: FragmentWebviewBinding? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentWebviewBinding.inflate(layoutInflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(KEY_DATA_URL)?.let {
            initWebview(it)
        }
    }

    private fun initWebview(url: String) {
        setTitle(url)
        viewBinding?.webview?.apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
            settings.loadsImagesAutomatically = true
            setBackgroundColor(Color.TRANSPARENT)
        }
        viewBinding?.webview?.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                viewBinding?.progressbar?.visibility = View.GONE
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                viewBinding?.progressbar?.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }


    private fun setTitle(url: String) {
        if (activity is MainActivity) {
            (activity as MainActivity).configToolbar(url)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_switch_language).apply {
            isVisible = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}