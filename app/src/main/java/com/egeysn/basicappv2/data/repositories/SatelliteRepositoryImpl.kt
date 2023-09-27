package com.egeysn.basicappv2.data.repositories

import android.content.Context
import com.egeysn.basicappv2.common.extension.fromJson
import com.egeysn.basicappv2.data.local.SatelliteDao
import com.egeysn.basicappv2.data.local.entities.SatelliteDetailEntity
import com.egeysn.basicappv2.data.remote.models.locations.SatelliteDto
import com.egeysn.basicappv2.data.remote.models.positions.PositionInfo
import com.egeysn.basicappv2.data.remote.models.positions.PositionResponse
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatelliteRepositoryImpl @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val gson: Gson,
    private val dao: SatelliteDao
) : SatelliteRepository {

    override suspend fun getSatellites(query: String): List<SatelliteDto> {
        val fileInString: String =
            appContext.assets.open("satellite_list.json").bufferedReader().use { it.readText() }
        val data = gson.fromJson<List<SatelliteDto>>(fileInString)
        return data.filter { it.name.lowercase().contains(query.lowercase()) }
    }

    override suspend fun getSatelliteDetail(id: Int): SatelliteDetailDto? {
        val fileInString: String =
            appContext.assets.open("satellite_detail.json").bufferedReader().use { it.readText() }
        val data = gson.fromJson<List<SatelliteDetailDto>>(fileInString)
        return data.find { it.id == id }
    }

    override suspend fun getPosition(id: Int): PositionInfo? {
        val fileInString: String =
            appContext.assets.open("positions.json").bufferedReader().use { it.readText() }
        val data = gson.fromJson<PositionResponse>(fileInString)
        return data.list.find { Integer.parseInt(it.id) == id }
    }

    override suspend fun insertSatelliteDetailToCache(entity: SatelliteDetailEntity) {
        dao.insertSatelliteDetail(entity)
    }

    override suspend fun getSatelliteDetailFromCache(id: Int): Flow<SatelliteDetailEntity?> {
        return dao.getSatelliteDetail(id)
    }
}
