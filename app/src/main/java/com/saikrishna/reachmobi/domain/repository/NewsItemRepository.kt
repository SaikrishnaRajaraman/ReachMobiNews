package com.saikrishna.reachmobi.domain.repository

import androidx.paging.PagingData
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsItemRepository {
    fun getNewsItems(page : Int = 1) : Flow<PagingData<NewsItem>>
    fun getFavorites() : Flow<List<NewsItem>>
    suspend fun toggleFavorite(item : NewsItem)
}