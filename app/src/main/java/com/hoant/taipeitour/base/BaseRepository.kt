package com.hoant.taipeitour.base

import com.hoant.taipeitour.repository.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


open class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                val message = throwable.message ?: kotlin.run { "" }
                Resource.Error(message, null)
            }
        }
    }

    suspend fun <T> apiCall(apiCall: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            apiCall.invoke()
        }
    }
}