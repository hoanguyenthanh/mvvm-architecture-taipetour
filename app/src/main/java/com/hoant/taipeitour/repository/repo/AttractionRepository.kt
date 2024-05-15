package com.hoant.taipeitour.repository.repo

import com.hoant.taipeitour.base.BaseRepository
import com.hoant.taipeitour.repository.api.service.AttractionApi


class AttractionRepository(private val api: AttractionApi) : BaseRepository(){
    suspend fun getAttractions(lang: String, page: Int) = apiCall {
        api.getAttractions(lang, page)
    }
}