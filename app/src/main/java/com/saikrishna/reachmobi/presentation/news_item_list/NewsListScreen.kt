package com.saikrishna.reachmobi.presentation.news_item_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.saikrishna.reachmobi.R
import com.saikrishna.reachmobi.data.model.NewsItem
import com.saikrishna.reachmobi.presentation.news_item_list.components.NewsListItem
import com.saikrishna.reachmobi.presentation.viewmodel.NewsViewModel
import com.saikrishna.reachmobi.ui.theme.ReachMobiTheme
import com.saikrishna.reachmobi.utils.NetworkUtils.isNetworkConnected

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsListScreen(
    onClick: (String?) -> Unit,
    listItems: LazyPagingItems<NewsItem>,
    onError : () -> Unit,
    viewModel: NewsViewModel
) {


    val isNetworkAvailable by rememberUpdatedState(newValue = isNetworkConnected(LocalContext.current))

    ReachMobiTheme {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
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


                    else -> {
                        if (listItems.loadState.refresh == LoadState.Loading) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        } else if(listItems.loadState.refresh is LoadState.Error)  {
                            onError()
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = stringResource(R.string.no_articles_found),
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        }
                        else {
                            if(listItems.itemCount == 0) {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        text = stringResource(R.string.no_articles_found),
                                        modifier = Modifier.align(
                                            Alignment.Center
                                        )
                                    )
                                }
                            } else {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    if (listItems.loadState.append == LoadState.Loading) {
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


}