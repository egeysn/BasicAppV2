package com.egeysn.basicappv2.data.remote.models.satellitedetail

import com.google.gson.annotations.SerializedName

data class SatelliteDetailDto(
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int,
    @SerializedName("first_flight")
    val firstFlight: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("mass")
    val mass: Int
)
