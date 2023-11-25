package com.example.watchedthat.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.SavedVisualMediaRepository
import com.example.watchedthat.model.SavedVisualMedia
import com.example.watchedthat.model.VisualMedia
import com.example.watchedthat.ui.screens.SavedVisualMediaUiState
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val savedVisualMediaRepository: SavedVisualMediaRepository
) : ViewModel() {
    var wishlistUiState: SavedVisualMediaUiState by mutableStateOf(
        SavedVisualMediaUiState.Loading
    )
        private set

    fun loadWishlist() {
        viewModelScope.launch {
            wishlistUiState = SavedVisualMediaUiState.Loading
            wishlistUiState = try {
                val wishlist = savedVisualMediaRepository.getWishlist()
                SavedVisualMediaUiState.Success(wishlist)
            } catch (e: Exception) {
                SavedVisualMediaUiState.Error
            }
        }
    }

    fun addToWatchedList(savedVisualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.addToWatchedList(savedVisualMedia as SavedVisualMedia)
        }
    }

    fun removeFromWishlist(savedVisualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.removeFromWishlist(savedVisualMedia as SavedVisualMedia)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WatchedThatApplication
                val savedVisualMediaRepository =
                    application.container.savedVisualMediaRepository
                WishlistViewModel(savedVisualMediaRepository)
            }
        }
    }
}