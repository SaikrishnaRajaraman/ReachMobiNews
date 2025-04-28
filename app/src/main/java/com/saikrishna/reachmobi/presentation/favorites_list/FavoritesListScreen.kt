package com.saikrishna.reachmobi.presentation.favorites_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.saikrishna.reachmobi.presentation.news_item_list.components.NewsListItem
import com.saikrishna.reachmobi.presentation.viewmodel.FavoritesViewModel
import com.saikrishna.reachmobi.ui.theme.ReachMobiTheme
import com.saikrishna.reachmobi.R
import com.saikrishna.reachmobi.data.model.NewsItem

@Composable
fun FavoritesListScreen(
    contentPadding: PaddingValues,
    onClick: (url: String?) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoritesList = viewModel.favoriteItems.collectAsState()


    var showConfirm by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<NewsItem?>(null) }

    ReachMobiTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {

            if (favoritesList.value.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(R.string.no_favorites_found),
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            } else {
                LazyColumn {
                    itemsIndexed(favoritesList.value) { index, item ->
                        NewsListItem(item, onClick = {
                            onClick(item.url)
                        }, onFavorite = {
                            // Change isFavorite to true and return the item
                            // Show confirmation alert and delete favorite
                            itemToDelete = item
                            showConfirm = true
                        }, isFavoriteScreen = true)
                    }
                }

                if (showConfirm && itemToDelete != null) {
                    AlertDialog(
                        onDismissRequest = { showConfirm = false },
                        title = { Text(stringResource(R.string.remove_favorite)) },
                        text = { Text(stringResource(R.string.confirm_remove_favorite)) },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.deleteFavorite(itemToDelete!!)
                                showConfirm = false
                            }) {
                                Text(stringResource(R.string.yes))
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showConfirm = false }) {
                                Text(stringResource(R.string.no))
                            }
                        }
                    )
                }
            }
        }
    }

}