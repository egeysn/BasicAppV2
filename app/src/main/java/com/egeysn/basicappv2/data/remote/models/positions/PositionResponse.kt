package com.egeysn.basicappv2.data.remote.models.positions

import com.google.gson.annotations.SerializedName

data class PositionResponse(
    @SerializedName("list")
    val list: List<PositionInfo>
)
