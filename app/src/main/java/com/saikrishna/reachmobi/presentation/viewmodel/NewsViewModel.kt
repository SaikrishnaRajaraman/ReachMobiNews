package com.saikrishna.reachmobi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.domain.usecase.GetFavoritesUseCase
import com.saikrishna.reachmobi.domain.usecase.GetNewsItemsUseCase
import com.saikrishna.reachmobi.domain.usecase.ToggleFavoriteUseCase
import com.saikrishna.reachmobi.utils.AnalyticsConstants
import com.saikrishna.reachmobi.utils.AnalyticsUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@dagger.hilt.android.lifecycle.HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsItemsUseCase: GetNewsItemsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoritesUseCase: ToggleFavoriteUseCase,
    private val analyticsUtils: AnalyticsUtils
) :
    ViewModel() {

    private val favorites = getFavoritesUseCase.invoke()

    private val searchQuery = MutableStateFlow("")


    fun setSearchQuery(query: String) {
        analyticsUtils.logEvent(AnalyticsConstants.EVENT_SEARCH_FEED)
        searchQuery.value = query
    }

    val favoriteUrls: Flow<Set<String>> =
        favorites.map { item -> item.map { it.url }.toSet() }


    @OptIn(ExperimentalCoroutinesApi::class)
    val newsPagingData: Flow<PagingData<NewsItem>> = searchQuery.flatMapLatest { query ->
        getNewsItemsUseCase(query = query)
            .cachedIn(viewModelScope)
    }.combine(favoriteUrls) { pagingData, favSet ->
        pagingData.map { item ->
            item.copy(isFavorite = item.url in favSet)
        }
    }


    fun toggleFavorites(item: NewsItem) {
        if (item.isFavorite) {
            analyticsUtils.logEvent(AnalyticsConstants.EVENT_REMOVE_FAVORITE)
        } else {
            analyticsUtils.logEvent(AnalyticsConstants.EVENT_ADD_TO_FAVORITE)
        }
        viewModelScope.launch {
            toggleFavoritesUseCase(item)
        }
    }


}