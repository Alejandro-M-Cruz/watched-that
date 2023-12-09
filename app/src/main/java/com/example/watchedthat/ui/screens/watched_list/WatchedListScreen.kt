package com.example.watchedthat.ui.screens.watched_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watchedthat.model.visual_media.VisualMedia
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.GenreFilters
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.SearchBar
import com.example.watchedthat.ui.components.VisualMediaGrid
import com.example.watchedthat.ui.screens.SavedVisualMediaUiState

@Composable
fun WatchedListScreen(
    watchedListViewModel: WatchedListViewModel,
    onNavigateToDetails: (VisualMedia) -> Unit
) {
    when (watchedListViewModel.watchedListUiState) {
        is SavedVisualMediaUiState.Success -> {
            val uiState = watchedListViewModel.watchedListUiState as SavedVisualMediaUiState.Success
            val groupedVisualMedia = uiState.visualMediaList
                .collectAsState(initial = emptyList())
                .value
                .groupBy { it.watchedMonth!! }
            val genres = uiState.genres.collectAsState(initial = emptyList()).value
            Column {
                SearchBar(
                    onSearch = watchedListViewModel::searchInWatchedList,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                if (genres.isNotEmpty()) {
                    GenreFilters(
                        genres = genres,
                        onSelectedGenres = watchedListViewModel::selectedGenresChanged
                    )
                }
                VisualMediaGrid (
                    groupedVisualMedia = groupedVisualMedia,
                    wishlistButtonOnClick = watchedListViewModel::addToWishlist,
                    watchedListButtonOnClick = watchedListViewModel::removeFromWatched,
                    onNavigateToDetails = onNavigateToDetails
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