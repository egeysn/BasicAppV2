package com.egeysn.basicappv2.domain.repositories

import com.egeysn.basicappv2.data.local.entities.SatelliteDetailEntity
import com.egeysn.basicappv2.data.remote.models.locations.SatelliteDto
import com.egeysn.basicappv2.data.remote.models.positions.PositionInfo
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto

interface SatelliteRepository {

    suspend fun getLocations(query: String): List<SatelliteDto>
    suspend fun getSatelliteDetail(id: Int): SatelliteDetailDto?
    suspend fun getPosition(id: Int): PositionInfo?
    suspend fun insertSatelliteDetailToCache(entity: SatelliteDetailEntity)
    suspend fun getSatelliteDetailFromCache(id: Int): SatelliteDetailEntity?
}
