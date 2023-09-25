package com.egeysn.basicappv2.domain.use_cases.detail

import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.handleError
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.mappers.SatelliteMapper
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber

class GetSatelliteDetailUseCaseImpl @Inject constructor(
    private val repository: SatelliteRepository,
    private val mapper: SatelliteMapper,
) : GetSatelliteDetailUseCase {
    override suspend fun getSatelliteDetail(id: Int): Flow<Resource<SatelliteDetailDto?>> = flow {
        try {
            emit(Resource.Loading())
            kotlinx.coroutines.delay(500)
            val cacheData = repository.getSatelliteDetailFromCache(id)
            cacheData?.let {
                Timber.d("Get satellite detail from cache")
                emit(Resource.Success(data = mapper.entityToSatelliteDetail(it)))
            } ?: run {
                Timber.d("Get satellite detail from json")
                val response = repository.getSatelliteDetail(id)
                emit(Resource.Success(data = response))
                response?.let { repository.insertSatelliteDetailToCache(entity = mapper.satelliteDetailToEntity(it)) }
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
