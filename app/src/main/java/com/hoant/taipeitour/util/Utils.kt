package com.hoant.taipeitour.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hoant.taipeitour.R

object Utils {
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
            .placeholder(R.mipmap.image_place_holder)
            .error(R.mipmap.image_place_holder)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)


        Glide.with(context)
            .load(url)
            .apply(requestOptions)
            .into(image)
    }
}