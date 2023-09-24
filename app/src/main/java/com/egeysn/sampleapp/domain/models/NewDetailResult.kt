package com.egeysn.sampleapp.domain.models

data class NewDetailResult(
    val id: Long,
    val publishedAt: String,
    val updatedAt: String,
    val title: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    var isFavorite: Boolean = false,
)
