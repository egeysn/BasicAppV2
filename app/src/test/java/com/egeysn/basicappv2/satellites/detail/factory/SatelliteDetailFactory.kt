package com.egeysn.basicappv2.satellites.detail.factory

import com.egeysn.basicappv2.data.local.entities.SatelliteDetailEntity
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto

class SatelliteDetailFactory {

    companion object {

        fun generateFakeResponse() = SatelliteDetailDto(
            costPerLaunch = 4000,
            firstFlight = "wisi",
            height = 7769,
            id = 9402,
            mass = 4426
        )

        fun generateCacheEntity() = SatelliteDetailEntity(
            costPerLaunch = 4000,
            firstFlight = "wisi",
            height = 7769,
            id = 9402,
            mass = 4426
        )

        fun dtoToEntity(dto: SatelliteDetailDto): SatelliteDetailEntity {
            return SatelliteDetailEntity(
                id = dto.id,
                costPerLaunch = dto.costPerLaunch,
                firstFlight = dto.firstFlight,
                height = dto.height,
                mass = dto.mass
            )
        }

        fun entityToDto(entity: SatelliteDetailEntity): SatelliteDetailDto {
            return SatelliteDetailDto(
                costPerLaunch = entity.costPerLaunch,
                firstFlight = entity.firstFlight,
                height = entity.height,
                id = entity.id,
                mass = entity.mass
            )
        }
    }
}
