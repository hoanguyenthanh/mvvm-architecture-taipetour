package com.hoant.taipeitour.repository.api.service

import com.hoant.taipeitour.repository.model.AttractionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AttractionApi {
     @GET("{lang}/Attractions/All")
     suspend fun getAttractions(@Path("lang") lang: String, @Query("page") page: Int): Response<AttractionResponse>
 }