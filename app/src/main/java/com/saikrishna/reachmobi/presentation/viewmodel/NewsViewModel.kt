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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@dagger.hilt.android.lifecycle.HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsItemsUseCase: GetNewsItemsUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoritesUseCase: ToggleFavoriteUseCase
) :
    ViewModel() {

    private val isLoadingFlow = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = isLoadingFlow


    private val favorites = getFavoritesUseCase.invoke()

    val favoriteUrls: Flow<Set<String>> =
        favorites.map { item -> item.map { it.url }.toSet() }


    val newsPagingData: Flow<PagingData<NewsItem>> =
        getNewsItemsUseCase()
            .cachedIn(viewModelScope)
            .combine(favoriteUrls) { pagingData , favSet ->
                pagingData.map { item ->
                    item.copy(isFavorite = item.url in favSet)
                }
            }



    fun addToFavorites(item: NewsItem) {
        viewModelScope.launch {
            toggleFavoritesUseCase(item)
        }
    }


}