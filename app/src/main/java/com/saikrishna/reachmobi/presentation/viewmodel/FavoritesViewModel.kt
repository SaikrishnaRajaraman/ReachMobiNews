package com.saikrishna.reachmobi.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.domain.usecase.GetFavoritesUseCase
import com.saikrishna.reachmobi.domain.usecase.GetNewsItemsUseCase
import com.saikrishna.reachmobi.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@dagger.hilt.android.lifecycle.HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val toggleFavoritesUseCase: ToggleFavoriteUseCase
) :
    ViewModel() {

    private val favorites = MutableStateFlow<List<NewsItem>>(emptyList())
    val favoriteItems: StateFlow<List<NewsItem>> = favorites

    init {
        viewModelScope.launch {
            getFavoritesUseCase.invoke().collectLatest { items ->
                favorites.value = items
            }
        }
    }


    fun deleteFavorite(item: NewsItem) {
        viewModelScope.launch {
            toggleFavoritesUseCase(item)
        }
    }


}