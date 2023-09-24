package com.egeysn.sampleapp.domain.mappers

import com.egeysn.sampleapp.common.extension.safeGet
import com.egeysn.sampleapp.data.local.entities.NewResultEntity
import com.egeysn.sampleapp.data.remote.models.news.NewResultDto
import com.egeysn.sampleapp.data.remote.models.response.NewDetailResponse
import com.egeysn.sampleapp.domain.models.NewDetailResult
import com.egeysn.sampleapp.domain.models.NewResult

class NewsMapper {

    fun fromDtoToDomain(newsDto: List<NewResultDto>, cachedFavorites: List<Long>): List<NewResult> {
        val arrayList: ArrayList<NewResult> = arrayListOf()
        newsDto.forEach {
            with(it) {
                arrayList.add(
                    NewResult(
                        id = id,
                        publishedAt = publishedAt.safeGet(),
                        title = title,
                        imageUrl = imageUrl.safeGet(),
                        newsSite = newsSite.safeGet(),
                        summary = summary.safeGet(),
                        isFavorite = cachedFavorites.find { id == it } != null,

                    ),
                )
            }
        }
        return arrayList
    }

    fun mapToNewDetail(newsDto: NewDetailResponse, isFavorite: Boolean = false): NewDetailResult {
        return with(newsDto) {
            NewDetailResult(
                id = id,
                publishedAt = publishedAt.safeGet(),
                updatedAt = updatedAt.safeGet(),
                title = title.safeGet(),
                imageUrl = imageUrl.safeGet(),
                newsSite = newsSite.safeGet(),
                summary = summary.safeGet(),
                isFavorite = isFavorite,
            )
        }
    }

    fun mapToNewResultDto(newsDto: NewDetailResult): NewResultEntity = with(newsDto) {
        NewResultEntity(
            id = id,
            publishedAt = publishedAt.safeGet(),
            title = title.safeGet(),
            imageUrl = imageUrl.safeGet(),
            newsSite = newsSite.safeGet(),
            summary = summary.safeGet(),

        )
    }

    fun mapToNewResultDto(newsDto: NewResult): NewResultEntity = with(newsDto) {
        NewResultEntity(
            id = id,
            publishedAt = publishedAt.safeGet(),
            title = title.safeGet(),
            imageUrl = imageUrl.safeGet(),
            newsSite = newsSite.safeGet(),
            summary = summary.safeGet(),

        )
    }
}
