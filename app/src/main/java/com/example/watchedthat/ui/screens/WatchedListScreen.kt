package com.example.watchedthat.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.SearchBar
import com.example.watchedthat.ui.components.VisualMediaGrid
import com.example.watchedthat.ui.viewmodel.WatchedListViewModel

@Composable
fun WatchedListScreen(watchedListViewModel: WatchedListViewModel) {
    when (watchedListViewModel.watchedListUiState) {
        is SavedVisualMediaUiState.Success -> {
            val visualMediaList =
                (watchedListViewModel.watchedListUiState as SavedVisualMediaUiState.Success)
                    .visualMediaList
            Column {
                SearchBar(
                    onSearch = watchedListViewModel::searchInWatchedList,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                VisualMediaGrid (
                    visualMediaList = visualMediaList.collectAsState(initial = listOf()).value,
                    wishlistButtonOnClick = watchedListViewModel::addToWishlist,
                    watchedListButtonOnClick = watchedListViewModel::removeFromWatched
                )
            }
        }
        is SavedVisualMediaUiState.Error ->
            ErrorScreen(
                errorMessage = "Could not load watched movies and TV shows",
                retryAction = watchedListViewModel::loadWatchedList
            )
        is SavedVisualMediaUiState.Loading -> LoadingScreen()
    }
}