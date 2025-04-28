package com.saikrishna.reachmobi.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.data.model.toNewsItem
import com.saikrishna.reachmobi.data.repository.NewsItemRemoteDataSource
import javax.inject.Inject

class NewsItemRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    NewsItemRemoteDataSource {

    override fun getNewsItemsPagingSource(page: Int): PagingSource<Int, NewsItem> {
        val pagingSource : PagingSource<Int, NewsItem> = object : PagingSource<Int, NewsItem>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
                val page = params.key ?: 1
                return try {
                    val resp = apiService.getNewsItems(page = page, country = "us")
                    val items = resp.articles.map { it.toNewsItem() }
                    LoadResult.Page(
                        data    = items,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (items.isEmpty()) null else page + 1
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? =
                state.anchorPosition?.let { anchor ->
                    state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                        ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
                }
        }

        return pagingSource
    }
}