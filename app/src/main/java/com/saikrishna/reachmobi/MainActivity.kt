@file:OptIn(ExperimentalMaterial3Api::class)

package com.saikrishna.reachmobi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.saikrishna.reachmobi.data.SearchHistoryManager
import com.saikrishna.reachmobi.presentation.favorites_list.FavoritesListScreen
import com.saikrishna.reachmobi.presentation.home.components.AppSearchBar
import com.saikrishna.reachmobi.presentation.models.BottomNavigationItem
import com.saikrishna.reachmobi.presentation.news_item_list.NewsListScreen
import com.saikrishna.reachmobi.presentation.viewmodel.NewsViewModel
import com.saikrishna.reachmobi.ui.theme.ReachMobiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var historyManager: SearchHistoryManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        historyManager = SearchHistoryManager(this)
        enableEdgeToEdge()
        setContent {

            val suggestions = remember { mutableStateListOf<String>() }
            LaunchedEffect(Unit) {
                suggestions.clear()
                suggestions += historyManager.getHistory()
            }

            val navController = rememberNavController()

            var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

            val searchTextFieldState = rememberTextFieldState()

            val navigationItems = listOf<BottomNavigationItem>(
                BottomNavigationItem(
                    title = stringResource(R.string.all_news),
                    selectedIcon = Icons.Filled.Newspaper,
                    unselectedIcon = Icons.Outlined.Newspaper
                ),
                BottomNavigationItem(
                    title = stringResource(R.string.favorites),
                    selectedIcon = Icons.Filled.Favorite,
                    unselectedIcon = Icons.Outlined.FavoriteBorder
                )
            )

            val newsViewModel: NewsViewModel = hiltViewModel()
            val listItems = newsViewModel.newsPagingData.collectAsLazyPagingItems()
            var searchExpanded by rememberSaveable { mutableStateOf(false) }

            ReachMobiTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(stringResource(R.string.app_name))
                            },
                            actions = {
                                // Refresh visible only in the all headlines screen
                                if (selectedItemIndex == 0) {
                                    IconButton(onClick = {
                                        newsViewModel.setSearchQuery("")
                                        listItems.refresh()
                                    }) {
                                        Icon(Icons.Default.Refresh, "")
                                    }
                                }
                            })

                    },
                    bottomBar = {
                        NavigationBar {
                            navigationItems.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigate(if (index == 0) AllNewsScreen else FavoritesScreen)
                                    },
                                    label = {
                                        Text(item.title)
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = item.title
                                        )
                                    }
                                )

                            }
                        }
                    }) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {

                        if(selectedItemIndex == 0 && !searchExpanded) {
                            AppSearchBar(
                                textFieldState = searchTextFieldState,
                                onSearch = { query ->
                                    newsViewModel.setSearchQuery(query)
                                    listItems.refresh()
                                    historyManager.addQuery(query)
                                    suggestions.clear()
                                    suggestions += historyManager.getHistory()
                                    searchExpanded = false

                                },
                                previousSearches = suggestions
                            )
                        }


                        NavHost(
                            navController = navController,
                            startDestination = AllNewsScreen
                        ) {
                            composable<AllNewsScreen> {
                                NewsListScreen(
                                    onClick = { url ->
                                        openArticle(url)
                                    },
                                    onError = {
                                        showErrorToast()

                                    },
                                    viewModel = newsViewModel,
                                    listItems = listItems
                                )
                            }
                            composable<FavoritesScreen> {
                                FavoritesListScreen(onClick = { url ->
                                    openArticle(url)
                                })
                            }
                        }
                    }


                }
            }
        }
    }
}

fun Context.openArticle(url: String?) {
    if (url.isNullOrEmpty()) {
        Toast.makeText(this, R.string.url_invalid, Toast.LENGTH_SHORT).show()
    } else {
        // Some devices don't support Custom Tabs. In that case it will crash with ActivityNotFound Exception
        try {
            val intent = CustomTabsIntent.Builder()
                .build();
            intent.launchUrl(this, url.toUri())
        } catch (e: Exception) {
            // If it crashes calling the browser intent
            openBrowserActivity(url)
        }

    }

}

fun Context.openBrowserActivity(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, url.toUri());
    this.startActivity(intent)
}

fun Context.showErrorToast() {
    Toast
        .makeText(
            this,
            R.string.error_toast,
            Toast.LENGTH_SHORT
        )
        .show()
}

@Serializable
object AllNewsScreen


@Serializable
object FavoritesScreen