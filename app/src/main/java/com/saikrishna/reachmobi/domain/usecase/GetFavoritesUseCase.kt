package com.saikrishna.reachmobi.domain.usecase

import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.domain.repository.NewsItemRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val repository: NewsItemRepository) {
    operator fun invoke(): Flow<List<NewsItem>> = repository.getFavorites()
}