package com.egeysn.basicappv2.domain.use_cases.search

import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.extension.handleError
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.domain.mappers.LocationsMapper
import com.egeysn.basicappv2.domain.repositories.LocationsRepository
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class GetLocationsUseCaseImpl @Inject constructor(
    private val repository: LocationsRepository,
    private val mapper: LocationsMapper,
) : GetLocationsUseCase {

    override suspend fun getLocations(query: String) = flow {
        try {
            emit(Resource.Loading())
            kotlinx.coroutines.delay(500)
            val response = repository.getLocations(query)
            emit(Resource.Success(data = mapper.mapToView(response)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }
}
