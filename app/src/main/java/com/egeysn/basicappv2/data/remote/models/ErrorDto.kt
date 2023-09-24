package com.egeysn.basicappv2.data.remote.models

import com.egeysn.basicappv2.domain.models.ErrorModel

data class ErrorDto(val detail: String?)

fun ErrorDto.toErrorModel(): ErrorModel = ErrorModel(error = detail)
