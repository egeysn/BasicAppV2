package com.egeysn.basicappv2.domain.repositories

import com.egeysn.basicappv2.data.remote.models.locations.LocationDto

interface LocationsRepository {

    suspend fun getLocations(query: String): List<LocationDto>
}
