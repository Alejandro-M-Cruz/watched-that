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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.watchedthat.R
import com.example.watchedthat.ui.screens.HomeScreen
import com.example.watchedthat.ui.viewmodel.HomeViewModel
import com.example.watchedthat.ui.screens.WatchedListScreen
import com.example.watchedthat.ui.viewmodel.WatchedListViewModel
import com.example.watchedthat.ui.screens.WishlistScreen
import com.example.watchedthat.ui.viewmodel.WishlistViewModel

enum class AppScreen(
    @StringRes val titleRes: Int,
    val icon: ImageVector,
    val iconDescription: String = "",
    @StringRes val labelRes: Int
) {
    Home(
        R.string.home_title,
        Icons.Filled.Home,
        iconDescription = "Home navigation icon",
        labelRes = R.string.home_label
    ),
    Wishlist(
        R.string.wishlist_title,
        Icons.Filled.Favorite,
        iconDescription = "Wishlist navigation icon",
        labelRes = R.string.wishlist_label
    ),
    WatchedList(
        R.string.watched_list_title,
        Icons.Filled.CheckCircle,
        iconDescription = "Watched list navigation icon",
        labelRes = R.string.watched_list_label
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchedThatApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.Home.name
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentScreen = currentScreen, navigate = { screen ->
                navController.navigate(screen.name)
            })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.Home.name) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                homeViewModel.loadTrendingVisualMedia()
                HomeScreen(homeViewModel)
            }
            composable(route = AppScreen.Wishlist.name) {
                val wishlistViewModel: WishlistViewModel =
                    viewModel(factory = WishlistViewModel.Factory)
                wishlistViewModel.loadWishlist()
                WishlistScreen(wishlistViewModel)
            }
            composable(route = AppScreen.WatchedList.name) {
                val watchedListViewModel: WatchedListViewModel =
                    viewModel(factory = WatchedListViewModel.Factory)
                watchedListViewModel.loadWatchedList()
                WatchedListScreen(watchedListViewModel)
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
                icon = { Icon(it.icon, contentDescription = it.iconDescription) },
                label = { Text(stringResource(id = it.labelRes)) }
            )
        }
    }
}

