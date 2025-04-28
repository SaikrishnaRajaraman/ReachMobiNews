package com.saikrishna.reachmobi.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saikrishna.reachmobi.data.model.NewsItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsItemDao {

    @Query("SELECT * FROM newsitems WHERE isFavorite = 1 ORDER BY publishedAt DESC")
    fun getFavorites(): Flow<List<NewsItem>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(items: List<NewsItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavorite(item: NewsItem)

    @Delete
    suspend fun delete(item: NewsItem)


}