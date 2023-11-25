package com.example.watchedthat.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.VisualMediaGrid

@Composable
fun WishlistScreen(wishlistViewModel: WishlistViewModel) {
    when (wishlistViewModel.wishlistUiState) {
        is SavedVisualMediaUiState.Success -> {
            val visualMediaList =
                (wishlistViewModel.wishlistUiState as SavedVisualMediaUiState.Success)
                    .visualMediaList
            VisualMediaGrid(
                visualMediaList = visualMediaList.collectAsState(initial = listOf()).value,
                wishlistButtonOnClick = wishlistViewModel::removeFromWishlist,
                watchedListButtonOnClick = wishlistViewModel::addToWatchedList
            )
        }
        is SavedVisualMediaUiState.Error ->
            ErrorScreen(
                errorMessage = "Could not load movies and TV shows in the wishlist",
                retryAction = wishlistViewModel::loadWishlist
            )
        is SavedVisualMediaUiState.Loading -> LoadingScreen()
    }
}