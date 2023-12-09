package com.example.watchedthat.ui.screens.wishlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.watchedthat.model.visual_media.VisualMedia
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.SearchBar
import com.example.watchedthat.ui.components.VisualMediaGrid
import com.example.watchedthat.ui.screens.SavedVisualMediaUiState

@Composable
fun WishlistScreen(
    wishlistViewModel: WishlistViewModel,
    onNavigateToDetails: (VisualMedia) -> Unit
) {
    when (wishlistViewModel.wishlistUiState) {
        is SavedVisualMediaUiState.Success -> {
            val visualMediaList =
                (wishlistViewModel.wishlistUiState as SavedVisualMediaUiState.Success)
                    .visualMediaList
            val groupedVisualMedia = visualMediaList
                .collectAsState(initial = listOf())
                .value
                .groupBy { it.addedToWishlistMonth!! }
            Column {
                SearchBar(
                    onSearch = wishlistViewModel::searchInWishlist,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
                VisualMediaGrid(
                    groupedVisualMedia = groupedVisualMedia,
                    wishlistButtonOnClick = wishlistViewModel::removeFromWishlist,
                    watchedListButtonOnClick = wishlistViewModel::addToWatchedList,
                    onNavigateToDetails = onNavigateToDetails
                )
            }
        }
        is SavedVisualMediaUiState.Error ->
            ErrorScreen(
                errorMessage = "Could not load movies and TV shows in the wishlist",
                retryAction = wishlistViewModel::loadWishlist
            )
        is SavedVisualMediaUiState.Loading -> LoadingScreen()
    }
}