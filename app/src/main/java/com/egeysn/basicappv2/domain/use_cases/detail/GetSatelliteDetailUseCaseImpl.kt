package com.egeysn.basicappv2.domain.use_cases.detail

import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.handleError
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.mappers.LocationsMapper
import com.egeysn.basicappv2.domain.repositories.LocationsRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetSatelliteDetailUseCaseImpl @Inject constructor(
    private val repository: LocationsRepository,
    private val mapper: LocationsMapper,
) : GetSatelliteDetailUseCase {
    override suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailDto?>> = flow {
        try {
            emit(Resource.Loading())
            kotlinx.coroutines.delay(500)
            val response = repository.getLocationDetail(id)
            emit(Resource.Success(data = response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
