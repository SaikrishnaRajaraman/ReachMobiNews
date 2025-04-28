package com.saikrishna.reachmobi.presentation.news_item_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.saikrishna.reachmobi.R
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.presentation.news_item_list.components.NewsListItem
import com.saikrishna.reachmobi.presentation.viewmodel.NewsViewModel
import com.saikrishna.reachmobi.ui.theme.ReachMobiTheme
import com.saikrishna.reachmobi.utils.NetworkUtils.isNetworkConnected

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    contentPadding: PaddingValues,
    onClick: (url: String?) -> Unit,
    onFavorite: (item: NewsItem) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {

    val listItems = viewModel.newsPagingData.collectAsLazyPagingItems()
    val isLoading by viewModel.isLoading.collectAsState()
    val hasMore by viewModel.hasMore.collectAsState()


    val isNetworkAvailable by rememberUpdatedState(newValue = isNetworkConnected(LocalContext.current))

    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    ReachMobiTheme {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            )
            {

                when {
                    !isNetworkAvailable -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                Icons.Filled.WifiOff,
                                contentDescription = "No Network",
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(stringResource(R.string.network_not_available))
                        }
                    }

                    isLoading -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            LazyColumn {
//
                                if (listItems.loadState.refresh == LoadState.Loading
                                    || listItems.loadState.append == LoadState.Loading
                                ) {
                                    item {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.align(Alignment.Center)
                                            )
                                        }

                                    }
                                }

                                items(count = listItems.itemCount) { index ->
                                    val item = listItems[index]
                                    if (item != null) {
                                        NewsListItem(item, onClick = {
                                            onClick(item.url)
                                        }, onFavorite = {
                                            // Change isFavorite to true and return the item
                                            viewModel.addToFavorites(item)
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}