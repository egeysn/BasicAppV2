package com.egeysn.basicappv2.data.remote.models.positions

import com.google.gson.annotations.SerializedName

data class Position(
    @SerializedName("posX")
    val posX: Double,
    @SerializedName("posY")
    val posY: Double
)
