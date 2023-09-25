package com.egeysn.basicappv2.domain.use_cases.satellites

import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.handleError
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.domain.mappers.SatelliteMapper
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetSatellitesUseCaseImpl @Inject constructor(
    private val repository: SatelliteRepository,
    private val mapper: SatelliteMapper,
) : GetSatellitesUseCase {

    override suspend fun getSatellites(query: String) = flow {
        try {
            emit(Resource.Loading())
            kotlinx.coroutines.delay(500)
            val response = repository.getSatellites(query)
            emit(Resource.Success(data = mapper.satellitesToView(response)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
