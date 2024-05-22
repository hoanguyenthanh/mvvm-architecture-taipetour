package com.hoant.taipeitour.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hoant.taipeitour.R

object Utils {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            loadImage(imageView.context, url, imageView)
        }
    }

    @BindingAdapter("textSpannable")
    @JvmStatic
    fun textSpannable(textView: TextView, textSpannable: String?) {
        textSpannable?.let {
            val span = SpannableString(textSpannable)
            span.setSpan(UnderlineSpan(), 0, textSpannable.length, 0)
            textView.text = span
        } ?: kotlin.run {
            textView.text = ""
        }
    }

    @BindingAdapter("textHtmlFormat")
    @JvmStatic
    fun textHtmlFormat(textView: TextView, textHtmlFormat: String?) {
        textHtmlFormat?.let {
            textView.text = HtmlCompat.fromHtml(textHtmlFormat, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

    fun hasInternetConnection(application: Application): Boolean {
        val connectivityManager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    fun loadImage(context: Context, url: String, image: ImageView) {
        val requestOptions: RequestOptions = RequestOptions()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_place_holder)
            .diskCacheStrategy(DiskCacheStrategy.NONE)


        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .into(image)
    }
}