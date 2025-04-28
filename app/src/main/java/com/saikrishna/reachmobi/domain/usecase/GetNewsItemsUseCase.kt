package com.saikrishna.reachmobi.domain.usecase

import androidx.paging.PagingData
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.domain.repository.NewsItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsItemsUseCase @Inject constructor(private val repository : NewsItemRepository) {

    operator fun invoke(page : Int = 1) : Flow<PagingData<NewsItem>> {
        return repository.getNewsItems(page)
    }

}