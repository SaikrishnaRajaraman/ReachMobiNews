package com.saikrishna.reachmobi.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.room.Query
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.data.repository.NewsItemLocalDataSource
import com.saikrishna.reachmobi.data.repository.NewsItemRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsItemRepositoryImpl @Inject constructor(
    private val remote: NewsItemRemoteDataSource,
    private val local: NewsItemLocalDataSource
) : NewsItemRepository {

    override fun getNewsItems(page: Int,query: String): Flow<PagingData<NewsItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { remote.getNewsItemsPagingSource(page,query) }
        ).flow
    }

    override fun getFavorites(): Flow<List<NewsItem>> {
        return local.getFavorites()
    }

    override suspend fun toggleFavorite(item: NewsItem) {
        if (item.isFavorite) {
            local.saveFavorite(item)
        } else {
            local.deleteFavorite(item)
        }

    }

}