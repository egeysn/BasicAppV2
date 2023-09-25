package com.egeysn.basicappv2.data.repositories

import android.content.Context
import com.egeysn.basicappv2.common.extension.fromJson
import com.egeysn.basicappv2.data.remote.models.locations.LocationDto
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.repositories.LocationsRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val gson: Gson
) : LocationsRepository {

    override suspend fun getLocations(query: String): List<LocationDto> {
        val fileInString: String =
            appContext.assets.open("satellite_list.json").bufferedReader().use { it.readText() }
        val data = gson.fromJson<List<LocationDto>>(fileInString)
        return data.filter { it.name.lowercase().contains(query.lowercase()) }
    }

    override suspend fun getLocationDetail(id: Int): SatelliteDetailDto? {
        val fileInString: String =
            appContext.assets.open("satellite_detail.json").bufferedReader().use { it.readText() }
        val data = gson.fromJson<List<SatelliteDetailDto>>(fileInString)
        return data.find { it.id == id }
    }
}
