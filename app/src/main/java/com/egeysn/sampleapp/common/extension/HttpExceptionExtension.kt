package com.egeysn.sampleapp.common.extension

import com.egeysn.sampleapp.R
import com.egeysn.sampleapp.common.utils.UiText
import com.egeysn.sampleapp.data.remote.models.ErrorDto
import com.egeysn.sampleapp.data.remote.models.toErrorModel
import com.google.gson.Gson
import retrofit2.HttpException

val gson = Gson()

@Synchronized
fun HttpException.handleError(): UiText {
    val errorString = this.response()?.errorBody()?.string()
    errorString?.let {
        val errorModel = gson.fromJson(errorString, ErrorDto::class.java)?.toErrorModel()
        if (errorModel?.error != null) {
            return UiText.DynamicString(errorModel.error)
        } else {
            return UiText.StringResource(R.string.unexpectedError)
        }
    }
    return this.localizedMessage?.let { UiText.DynamicString(it) }
        ?: UiText.StringResource(R.string.unexpectedError)
}
