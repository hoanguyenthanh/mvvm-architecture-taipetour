package com.hoant.taipeitour.viewmodel.attraction

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hoant.taipeitour.repository.model.Resource
import com.hoant.taipeitour.util.Utils.hasInternetConnection
import com.hoant.taipeitour.MyApplication
import com.hoant.taipeitour.R
import com.hoant.taipeitour.repository.model.AttractionResponse
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.base.BaseViewModel
import kotlinx.coroutines.launch
import java.io.IOException

class AttractionViewModel(
    app: Application,
    private val attractionRepository: AttractionRepository
) : BaseViewModel(app) {

    val attractionsResponse: MutableLiveData<Resource<AttractionResponse>> = MutableLiveData()

    fun getAttractions(lang: String, page: Int) = viewModelScope.launch {
        fetchAttractions(lang, page)
    }


    private suspend fun fetchAttractions(lang: String, page: Int) {
        attractionsResponse.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MyApplication>())) {
                val response = attractionRepository.getAttractions(lang, page)
                if (response.isSuccessful) {
                    response.body()?.let {
                        attractionsResponse.postValue(Resource.Success(it))
                    }
                } else {
                    attractionsResponse.postValue(Resource.Error(response.message()))
                }
            } else {
                attractionsResponse.postValue(Resource.Error(getStringRes(R.string.no_internet_connection)))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> attractionsResponse.postValue(
                    Resource.Error(getStringRes(R.string.network_failure))
                )
                else -> attractionsResponse.postValue(
                    Resource.Error(getStringRes(R.string.conversion_error))
                )
            }
        }
    }
}