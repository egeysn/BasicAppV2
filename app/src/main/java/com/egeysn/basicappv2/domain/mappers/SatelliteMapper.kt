package com.egeysn.basicappv2.domain.mappers

import com.egeysn.basicappv2.data.local.entities.SatelliteDetailEntity
import com.egeysn.basicappv2.data.remote.models.locations.SatelliteDto
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.models.SatelliteItem

class SatelliteMapper {

    fun satellitesToView(response: List<SatelliteDto>): List<SatelliteItem> {
        return response.map { SatelliteItem(it.active, it.id, it.name) }
    }

    fun satelliteDetailToEntity(data: SatelliteDetailDto): SatelliteDetailEntity {
        return SatelliteDetailEntity(
            data.id, data.costPerLaunch,
            data.firstFlight,
            height = data.height,
            mass = data.mass
        )
    }

    fun entityToSatelliteDetail(data: SatelliteDetailEntity): SatelliteDetailDto {
        return SatelliteDetailDto(
            id = data.id,
            costPerLaunch = data.costPerLaunch,
            firstFlight = data.firstFlight,
            height = data.height,
            mass = data.mass
        )
    }
}
