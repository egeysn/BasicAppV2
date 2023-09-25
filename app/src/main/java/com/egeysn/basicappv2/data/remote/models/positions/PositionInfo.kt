package com.egeysn.basicappv2.data.remote.models.positions

import com.google.gson.annotations.SerializedName

class PositionInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("positions")
    val positions: List<Position>
)
