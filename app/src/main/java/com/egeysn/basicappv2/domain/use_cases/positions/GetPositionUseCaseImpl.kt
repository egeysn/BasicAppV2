package com.egeysn.basicappv2.domain.use_cases.positions

import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.handleError
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.positions.PositionInfo
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetPositionUseCaseImpl @Inject constructor(
    private val repository: SatelliteRepository
) : GetPositionUseCase {

    override suspend fun getPosition(id: Int): Flow<Resource<PositionInfo?>> = flow {
        try {
            emit(Resource.Loading())
            kotlinx.coroutines.delay(500)
            val response = repository.getPosition(id)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
