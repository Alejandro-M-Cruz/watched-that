package com.example.watchedthat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.SearchBar
import com.example.watchedthat.ui.components.VisualMediaGrid
import com.example.watchedthat.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    when (homeViewModel.visualMediaUiState) {
        is VisualMediaUiState.Success -> {
            val uiState =
                (homeViewModel.visualMediaUiState as VisualMediaUiState.Success)
            Column {
                SearchBar(
                    onSearch = homeViewModel::searchVisualMedia,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                VisualMediaGrid(
                    visualMediaList = uiState.visualMediaList,
                    onEndReached = homeViewModel::loadMoreResults,
                    wishlistButtonOnClick = homeViewModel::addToWishList,
                    watchedListButtonOnClick = homeViewModel::addToWatchedList
                )
            }
        }
        is VisualMediaUiState.Error ->
            ErrorScreen(
                errorMessage = "Error loading",
                retryAction = homeViewModel::loadTrendingVisualMedia
            )
        is VisualMediaUiState.Loading -> LoadingScreen()
    }
}
