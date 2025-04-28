package com.saikrishna.reachmobi.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.data.model.toNewsItem

class NewsItemPagingSource(private val apiService: ApiService) :
    PagingSource<Int, NewsItem>() {

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition?.let { pos ->
            state.closestPageToPosition(pos)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(pos)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> = try {
        val currentPage = params.key ?: 1
        val newsItemDto = apiService.getNewsItems(currentPage, "us")

        val items = newsItemDto.articles.map { it.toNewsItem() }

        LoadResult.Page(
            data = items,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (items.isEmpty()) null else currentPage + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

}