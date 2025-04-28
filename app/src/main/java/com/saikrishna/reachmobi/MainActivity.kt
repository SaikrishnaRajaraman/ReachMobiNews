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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saikrishna.reachmobi.presentation.favorites_list.FavoritesListScreen
import com.saikrishna.reachmobi.presentation.home.components.AppSearchBar
import com.saikrishna.reachmobi.presentation.models.BottomNavigationItem
import com.saikrishna.reachmobi.presentation.news_item_list.NewsListScreen
import com.saikrishna.reachmobi.ui.theme.ReachMobiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

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

            ReachMobiTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        AppSearchBar(textFieldState = searchTextFieldState, onSearch = {

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
                    NavHost(
                        navController = navController,
                        startDestination = AllNewsScreen
                    ) {
                        composable<AllNewsScreen> {
                            NewsListScreen(onClick = { url ->
                                openArticle(url)
                            }, onFavorite = {

                            }, contentPadding = innerPadding)
                        }
                        composable<FavoritesScreen> {
                            FavoritesListScreen(innerPadding, onClick = { url ->
                                openArticle(url)
                            })
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

@Serializable
object AllNewsScreen


@Serializable
object FavoritesScreen