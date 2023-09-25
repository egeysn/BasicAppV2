package com.egeysn.basicappv2.domain.mappers

import com.egeysn.basicappv2.data.remote.models.locations.LocationDto
import com.egeysn.basicappv2.domain.models.LocationItem

class LocationsMapper {

    fun locationsToView(response: List<LocationDto>): List<LocationItem> {
        return response.map { LocationItem(it.active, it.id, it.name) }
    }
}
