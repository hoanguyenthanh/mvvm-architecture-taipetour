package com.hoant.taipeitour.repository.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


open class Interceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}