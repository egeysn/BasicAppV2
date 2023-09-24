package com.egeysn.basicappv2.data.remote.models.locations

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
