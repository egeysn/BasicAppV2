package com.egeysn.basicappv2.domain.use_cases.detail

import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import kotlinx.coroutines.flow.Flow

interface GetSatelliteDetailUseCase {
    suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailDto?>>
}
