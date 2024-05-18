package com.hoant.taipeitour.view.webview

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hoant.taipeitour.databinding.FragmentWebviewBinding

class WebviewFragment: Fragment() {
    companion object {
        val KEY_DATA_URL = "key_data_url"
    }

    private var binding: FragmentWebviewBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentWebviewBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(KEY_DATA_URL)?.let {
            initWebview(it)
        }
    }

    private fun initWebview(url: String) {
        binding?.webview?.apply {
            loadUrl(url)
            settings.allowContentAccess = true
            settings.allowFileAccess = true
            settings.javaScriptEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}