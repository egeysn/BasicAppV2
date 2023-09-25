package com.egeysn.basicappv2.domain.repositories

import com.egeysn.basicappv2.data.remote.models.locations.LocationDto
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto

interface LocationsRepository {

    suspend fun getLocations(query: String): List<LocationDto>
    suspend fun getLocationDetail(id: Int): SatelliteDetailDto?
}
