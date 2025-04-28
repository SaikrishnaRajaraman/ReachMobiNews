package com.saikrishna.reachmobi.data.repository

import com.saikrishna.reachmobi.data.model.NewsItem
import kotlinx.coroutines.flow.Flow

interface NewsItemLocalDataSource {
    suspend fun saveNews(items: List<NewsItem>)
    fun getFavorites(): Flow<List<NewsItem>>
    suspend fun saveFavorite(item: NewsItem)
    suspend fun deleteFavorite(item: NewsItem)
}