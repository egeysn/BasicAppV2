package com.egeysn.sampleapp.domain.repositories

import com.egeysn.sampleapp.data.local.entities.NewResultEntity
import com.egeysn.sampleapp.data.remote.models.response.NewDetailResponse
import com.egeysn.sampleapp.data.remote.models.response.NewsResponse

interface NewsRepository {
    suspend fun getNews(page: Int): NewsResponse

    suspend fun searchNews(query: String, page: Int): NewsResponse

    suspend fun getNewDetail(id: Long): NewDetailResponse

    suspend fun getFavoriteNewsFromDatabase(): List<NewResultEntity>

    suspend fun insertFavoriteNew(entity: NewResultEntity)

    suspend fun deleteFavoriteNew(id: Long)
}
