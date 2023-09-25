package com.egeysn.basicappv2.domain.use_cases.satellites

import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.domain.models.SatelliteItem
import kotlinx.coroutines.flow.Flow

interface GetSatellitesUseCase {
    suspend fun getSatellites(query: String): Flow<Resource<List<SatelliteItem>>>
}
