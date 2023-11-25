package com.example.watchedthat.ui.screens

import androidx.compose.runtime.Composable
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.VisualMediaGrid

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    when (homeViewModel.visualMediaUiState) {
        is VisualMediaUiState.Success -> {
            val visualMediaList =
                (homeViewModel.visualMediaUiState as VisualMediaUiState.Success).visualMediaList
            VisualMediaGrid(
                visualMediaList = visualMediaList,
                onEndReached = { homeViewModel.loadTrendingVisualMedia(isFirstRequest = false) },
                wishlistButtonOnClick = homeViewModel::addToWishList,
                watchedListButtonOnClick = homeViewModel::addToWatchedList
            )
        }
        is VisualMediaUiState.Error ->
            ErrorScreen(
                errorMessage = "Could not load trending movies and TV shows",
                retryAction = homeViewModel::loadTrendingVisualMedia
            )
        is VisualMediaUiState.Loading -> LoadingScreen()
    }
}
