package com.hoant.taipeitour.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hoant.taipeitour.repository.repo.AttractionRepository
import com.hoant.taipeitour.viewmodel.attraction.AttractionViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelProviderFactory(
    val app: Application,
    val repository: BaseRepository
) : ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AttractionViewModel::class.java)) {
            return AttractionViewModel(app, repository as AttractionRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}