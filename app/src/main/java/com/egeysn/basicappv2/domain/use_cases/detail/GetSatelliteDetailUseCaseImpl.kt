package com.egeysn.basicappv2.domain.use_cases.detail

import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.handleError
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.mappers.SatelliteMapper
import com.egeysn.basicappv2.domain.repositories.SatelliteRepository
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
            coroutineScope {
                repository.getSatelliteDetailFromCache(id).collect(){ cacheData->
                    cacheData?.let {
                        Timber.w("Get satellite detail from CACHE")
                        emit(Resource.Success(data = mapper.entityToSatelliteDetail(it)))
                    } ?: run {
                        Timber.w("Get satellite detail from JSON")
                        val response = repository.getSatelliteDetail(id)
                        emit(Resource.Success(data = response))
                        response?.let { repository.insertSatelliteDetailToCache(entity = mapper.satelliteDetailToEntity(it)) }
                    }
                }
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
