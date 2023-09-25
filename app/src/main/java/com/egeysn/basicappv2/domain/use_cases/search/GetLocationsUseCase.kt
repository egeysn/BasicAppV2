package com.egeysn.basicappv2.domain.use_cases.search

import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.domain.models.SatelliteItem
import kotlinx.coroutines.flow.Flow

interface GetLocationsUseCase {
    suspend fun getLocations(query: String): Flow<Resource<List<SatelliteItem>>>
}
