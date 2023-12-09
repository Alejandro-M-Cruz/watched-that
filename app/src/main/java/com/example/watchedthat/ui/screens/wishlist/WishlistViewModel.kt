package com.example.watchedthat.ui.screens.wishlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.watchedthat.WatchedThatApplication
import com.example.watchedthat.data.GenresRepository
import com.example.watchedthat.data.SavedVisualMediaRepository
import com.example.watchedthat.model.genre.Genre
import com.example.watchedthat.model.visual_media.SavedVisualMedia
import com.example.watchedthat.model.visual_media.VisualMedia
import com.example.watchedthat.ui.screens.SavedVisualMediaUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WishlistViewModel(
    private val savedVisualMediaRepository: SavedVisualMediaRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {
    var wishlistUiState: SavedVisualMediaUiState by mutableStateOf(
        SavedVisualMediaUiState.Loading
    )
        private set
    private lateinit var wishlist: Flow<List<SavedVisualMedia>>

    init {
        loadWishlist()
    }

    fun loadWishlist() {
        viewModelScope.launch {
            wishlistUiState = SavedVisualMediaUiState.Loading
            wishlistUiState = try {
                wishlist = savedVisualMediaRepository.getWishlist()
                val genres = genresRepository.getStoredGenres()
                SavedVisualMediaUiState.Success(wishlist, genres)
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

    fun searchInWishlist(query: String) {
        viewModelScope.launch {
            val uiState = wishlistUiState as SavedVisualMediaUiState.Success
            wishlistUiState = SavedVisualMediaUiState.Loading
            wishlistUiState = try {
                wishlist = savedVisualMediaRepository.searchInWishlist(query)
                uiState.copy(visualMediaList = wishlist)
            } catch (e: Exception) {
                SavedVisualMediaUiState.Error
            }
        }
    }

    fun selectedGenresChanged(genres: Set<Genre>) {
        val uiState = wishlistUiState as SavedVisualMediaUiState.Success
        wishlistUiState = uiState.copy(
            visualMediaList = wishlist.map {
                it.filter { visualMedia -> visualMedia.hasAnyGenreOf(genres) }
            }
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WatchedThatApplication
                val savedVisualMediaRepository =
                    application.container.savedVisualMediaRepository
                val genresRepository = application.container.genresRepository
                WishlistViewModel(savedVisualMediaRepository, genresRepository)
            }
        }
    }
}