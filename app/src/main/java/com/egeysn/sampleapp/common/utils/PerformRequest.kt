package com.egeysn.sampleapp.common.utils

import com.egeysn.sampleapp.R
import com.egeysn.sampleapp.common.extension.handleError
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

fun <T, K> performRequest(mapper: (response: T) -> K, networkCall: suspend () -> T): Flow<Resource<K>> = flow {
    try {
        emit(Resource.Loading())
        val response = networkCall.invoke()
        emit(Resource.Success(data = mapper(response)))
    } catch (e: HttpException) {
        emit(Resource.Error(e.handleError()))
    } catch (e: IOException) {
        emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
    }
}
