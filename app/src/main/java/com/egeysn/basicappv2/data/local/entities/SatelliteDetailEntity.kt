package com.egeysn.basicappv2.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "satelliteDetail")
data class SatelliteDetailEntity(
    @PrimaryKey val id: Int,
    @SerializedName("cost_per_launch")
    val costPerLaunch: Int,
    @SerializedName("first_flight")
    val firstFlight: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("mass")
    val mass: Int
)
