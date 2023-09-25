package com.egeysn.basicappv2.domain.use_cases.positions

import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.data.remote.models.positions.PositionInfo
import kotlinx.coroutines.flow.Flow

interface GetPositionUseCase {
    suspend fun getPosition(id: Int): Flow<Resource<PositionInfo?>>
}
