package com.saikrishna.reachmobi.domain.usecase

import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.domain.repository.NewsItemRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: NewsItemRepository) {
    suspend operator fun invoke(item: NewsItem) {
        repository.toggleFavorite(item.copy(isFavorite = !item.isFavorite))
    }

}