package com.egeysn.basicappv2.data.remote.models.locations

import com.google.gson.annotations.SerializedName

data class SatelliteDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("name")
    val name: String
)
