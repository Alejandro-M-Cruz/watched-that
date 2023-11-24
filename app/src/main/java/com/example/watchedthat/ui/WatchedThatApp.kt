package com.example.watchedthat.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.watchedthat.R
import com.example.watchedthat.ui.screens.HomeScreen
import com.example.watchedthat.ui.screens.HomeViewModel

enum class AppScreen(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val iconDescription: String = ""
) {
    HOME(R.string.home_title, Icons.Filled.Home, "Home navigation icon"),
    WISHLIST(R.string.wishlist_title, Icons.Filled.Favorite, "Wishlist navigation icon"),
    WATCHED_LIST(R.string.watched_list_title, Icons.Filled.CheckCircle, "Watched list navigation icon")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchedThatApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.HOME.name
    )

    Scaffold(
        topBar = {
             TopSearchBar(currentScreen = currentScreen)
        },
        bottomBar = {
            BottomNavigationBar(currentScreen = currentScreen, navigate = { screen ->
                navController.navigate(screen.name)
            })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.HOME.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.HOME.name) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                homeViewModel.loadTrendingVisualMedia()
                HomeScreen(
                    homeViewModel.visualMediaRetrievalState,
                    retryAction = homeViewModel::loadTrendingVisualMedia
                )
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchBar(currentScreen: AppScreen) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = currentScreen.titleRes),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search icon")
            }
        }
    )
}

@Composable
fun BottomNavigationBar(currentScreen: AppScreen, navigate: (AppScreen) -> Unit) {
    NavigationBar {
        AppScreen.values().forEach {
            NavigationBarItem(
                selected = currentScreen == it,
                onClick = { navigate(it) },
                icon = { Icon(it.icon, contentDescription = it.iconDescription) }
            )
        }
    }
}

