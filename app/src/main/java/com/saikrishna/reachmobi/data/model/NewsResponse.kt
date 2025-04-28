package com.saikrishna.reachmobi.data.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsItemDto>
)
