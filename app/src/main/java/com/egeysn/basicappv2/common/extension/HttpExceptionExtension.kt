package com.egeysn.basicappv2.common.extension

import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.ErrorDto
import com.egeysn.basicappv2.data.remote.models.toErrorModel
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
