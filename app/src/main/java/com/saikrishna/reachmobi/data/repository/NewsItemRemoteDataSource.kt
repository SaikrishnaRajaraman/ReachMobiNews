package com.saikrishna.reachmobi.data.repository

import androidx.paging.PagingSource
import com.saikrishna.reachmobi.data.model.NewsItem

interface NewsItemRemoteDataSource {
    fun getNewsItemsPagingSource(page: Int = 1): PagingSource<Int, NewsItem>
}