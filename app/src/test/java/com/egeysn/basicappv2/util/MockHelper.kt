package com.egeysn.newsapp.domain.util

import java.io.IOException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

class MockHelper {

    companion object {
        private const val errorJson = "{\"detail\":\"\"}"
        val ioException = IOException()

        fun getHttpException(): HttpException {
            return HttpException(
                Response.error<ResponseBody>(500, errorJson.toResponseBody("plain/text".toMediaTypeOrNull())),
            )
        }
    }
}
