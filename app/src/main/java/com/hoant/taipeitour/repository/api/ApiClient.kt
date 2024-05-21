package com.hoant.taipeitour.repository.api

import com.hoant.taipeitour.repository.api.service.AttractionApi
import com.hoant.taipeitour.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    companion object {
        private val client by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().apply {
                addInterceptor(logging)
                addInterceptor(Interceptor())
            }

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }

        val attractionApi: AttractionApi by lazy {
            client.create(AttractionApi::class.java)
        }
    }
}