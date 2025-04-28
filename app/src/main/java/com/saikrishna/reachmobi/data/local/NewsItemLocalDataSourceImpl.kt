package com.saikrishna.reachmobi.data.local

import com.saikrishna.reachmobi.data.dao.NewsItemDao
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.data.repository.NewsItemLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsItemLocalDataSourceImpl @Inject constructor(private val dao: NewsItemDao) :
    NewsItemLocalDataSource {
    override suspend fun saveNews(items: List<NewsItem>) {
        dao.insertNews(items)
    }

    override fun getFavorites(): Flow<List<NewsItem>> {
        return dao.getFavorites()
    }

    override suspend fun saveFavorite(item: NewsItem) {
        dao.saveFavorite(item)
    }

    override suspend fun deleteFavorite(item : NewsItem) {
        dao.delete(item)
    }



}