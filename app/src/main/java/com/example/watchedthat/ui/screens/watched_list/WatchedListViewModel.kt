package com.example.watchedthat.ui.screens.watched_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WatchedListViewModel(
    private val savedVisualMediaRepository: SavedVisualMediaRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {
    var watchedListUiState: SavedVisualMediaUiState by mutableStateOf(
        SavedVisualMediaUiState.Loading
    )
        private set
    private lateinit var watchedList: Flow<List<SavedVisualMedia>>

    init {
        loadWatchedList()
    }

    fun loadWatchedList() {
        viewModelScope.launch {
            watchedListUiState = SavedVisualMediaUiState.Loading
            watchedListUiState = try {
                watchedList = savedVisualMediaRepository.getWatchedList()
                SavedVisualMediaUiState.Success(
                    visualMediaList = watchedList,
                    genres = genresRepository.getStoredGenres()
                )
            } catch (e: Exception) {
                SavedVisualMediaUiState.Error
            }
        }
    }

    fun addToWishlist(savedVisualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.addToWishlist(savedVisualMedia as SavedVisualMedia)
        }
    }

    fun removeFromWatched(savedVisualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.removeFromWatchedList(savedVisualMedia as SavedVisualMedia)
        }
    }

    fun searchInWatchedList(query: String) {
        viewModelScope.launch {
            val uiState = watchedListUiState as SavedVisualMediaUiState.Success
            watchedListUiState = SavedVisualMediaUiState.Loading
            watchedListUiState = try {
                watchedList = savedVisualMediaRepository.searchInWatchedList(query)
                uiState.copy(visualMediaList = watchedList)
            } catch (e: Exception) {
                SavedVisualMediaUiState.Error
            }
        }
    }

    fun selectedGenresChanged(genres: Set<Genre>) {
        val uiState = watchedListUiState as SavedVisualMediaUiState.Success
        if (genres.isEmpty()) {
            watchedListUiState = uiState.copy(visualMediaList = flowOf(emptyList()))
            return
        }
        watchedListUiState = uiState.copy(
            visualMediaList = watchedList.map {
                it.filter { visualMedia -> visualMedia.hasAnyGenreOf(genres) }
            }
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val savedVisualMediaRepository =
                    application.container.savedVisualMediaRepository
                val genresRepository = application.container.genresRepository
                WatchedListViewModel(savedVisualMediaRepository, genresRepository)
            }
        }
    }
}