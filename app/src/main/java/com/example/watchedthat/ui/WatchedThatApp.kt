package com.example.watchedthat.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watchedthat.R
import com.example.watchedthat.model.MediaType
import com.example.watchedthat.model.visualmedia.VisualMedia
import com.example.watchedthat.ui.screens.home.HomeScreen
import com.example.watchedthat.ui.screens.movie_details.MovieDetailsScreen
import com.example.watchedthat.ui.screens.tv_show_details.TvShowDetailsScreen
import com.example.watchedthat.ui.screens.watched_list.WatchedListScreen
import com.example.watchedthat.ui.screens.wishlist.WishlistScreen
import com.example.watchedthat.ui.screens.home.HomeViewModel
import com.example.watchedthat.ui.screens.movie_details.MovieDetailsViewModel
import com.example.watchedthat.ui.screens.tv_show_details.TvShowDetailsViewModel
import com.example.watchedthat.ui.screens.watched_list.WatchedListViewModel
import com.example.watchedthat.ui.screens.wishlist.WishlistViewModel

enum class AppScreen(
    val route: String,
    @StringRes val titleRes: Int,
    val inNavigationBar: Boolean = false,
    @StringRes val labelRes: Int? = null,
    val icon: ImageVector? = null,
    val iconDescription: String = "",
    val hasTopBar: Boolean = false,
) {
    Home(
        route = "home",
        titleRes = R.string.home_title,
        inNavigationBar = true,
        labelRes = R.string.home_label,
        icon = Icons.Filled.Home,
        iconDescription = "Home navigation icon"
    ),
    Wishlist(
        route = "wishlist",
        titleRes = R.string.wishlist_title,
        inNavigationBar = true,
        labelRes = R.string.wishlist_label,
        icon = Icons.Filled.Favorite,
        iconDescription = "Wishlist navigation icon"
    ),
    WatchedList(
        route = "watched_list",
        titleRes = R.string.watched_list_title,
        inNavigationBar = true,
        labelRes = R.string.watched_list_label,
        icon = Icons.Filled.CheckCircle,
        iconDescription = "Watched list navigation icon"
    ),
    MovieDetails(
        route = "movie/{movie_id}",
        titleRes = R.string.movie_details_title,
        inNavigationBar = false,
        hasTopBar = true
    ),
    TvShowDetails(
        route = "tv_show/{tv_show_id}",
        titleRes = R.string.tv_show_details_title,
        inNavigationBar = false,
        hasTopBar = true
    ),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchedThatApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.values().find { screen ->
        backStackEntry?.destination?.hierarchy?.any {
            it.route == screen.route
        } == true
    } ?: AppScreen.Home
    val onNavigateToDetails = { visualMedia: VisualMedia ->
        when (visualMedia.mediaType) {
            MediaType.MOVIE -> navController.navigate("movie/${visualMedia.id}")
            MediaType.TV_SHOW -> navController.navigate("tv_show/${visualMedia.id}")
            else -> { }
        }
    }

    Scaffold(
        topBar = {
            if (currentScreen.hasTopBar) {
                AppTopBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateBack = { navController.navigateUp() }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(currentScreen = currentScreen, navigate = { screen ->
                navController.navigate(screen.route)
            })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = AppScreen.Home.route) {
                val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
                HomeScreen(homeViewModel, onNavigateToDetails)
            }
            composable(route = AppScreen.Wishlist.route) {
                val wishlistViewModel: WishlistViewModel =
                    viewModel(factory = WishlistViewModel.Factory)
                WishlistScreen(wishlistViewModel, onNavigateToDetails)
            }
            composable(route = AppScreen.WatchedList.route) {
                val watchedListViewModel: WatchedListViewModel =
                    viewModel(factory = WatchedListViewModel.Factory)
                WatchedListScreen(watchedListViewModel, onNavigateToDetails)
            }
            composable(
                route = AppScreen.MovieDetails.route,
                arguments = listOf(navArgument("movie_id") { type = NavType.IntType })
            ) {
                val movieDetailsViewModel: MovieDetailsViewModel =
                    viewModel(factory = MovieDetailsViewModel.Factory)
                MovieDetailsScreen(movieDetailsViewModel)
            }
            composable(
                route = AppScreen.TvShowDetails.route,
                arguments = listOf(navArgument("tv_show_id") { type = NavType.IntType })
            ) {
                val tvShowDetailsViewModel: TvShowDetailsViewModel =
                    viewModel(factory = TvShowDetailsViewModel.Factory)
                TvShowDetailsScreen(tvShowDetailsViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentScreen: AppScreen,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,
    navigateBack: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = currentScreen.titleRes),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back icon"
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun BottomNavigationBar(currentScreen: AppScreen, navigate: (AppScreen) -> Unit) {
    NavigationBar {
        AppScreen.values().filter { it.inNavigationBar }.forEach {
            NavigationBarItem(
                selected = currentScreen == it,
                onClick = { navigate(it) },
                icon = { Icon(it.icon!!, contentDescription = it.iconDescription) },
                label = { Text(stringResource(id = it.labelRes!!)) }
            )
        }
    }
}

