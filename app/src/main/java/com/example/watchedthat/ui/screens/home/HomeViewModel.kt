package com.example.watchedthat.ui.screens.home

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
import com.example.watchedthat.data.VisualMediaRepository
import com.example.watchedthat.model.genre.Genre
import com.example.watchedthat.model.visual_media.VisualMedia
import kotlinx.coroutines.launch

sealed interface VisualMediaUiState {
    data class Success(
        val visualMediaList: List<VisualMedia>,
        val resultType: ResultType,
        val genres: List<Genre>,
        val query: String? = null
    ) : VisualMediaUiState
    object Loading : VisualMediaUiState
    object Error : VisualMediaUiState
}

enum class ResultType(val title: String) {
    Trending("Trending today"),
    Search("Search results")
}

class HomeViewModel(
    private val visualMediaRepository: VisualMediaRepository,
    private val savedVisualMediaRepository: SavedVisualMediaRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {
    var visualMediaUiState: VisualMediaUiState by mutableStateOf(VisualMediaUiState.Loading)
        private set
    private var selectedGenres: Set<Genre> = emptySet()

    init {
        loadUiState()
    }

    fun loadUiState() {
        viewModelScope.launch {
            visualMediaUiState = VisualMediaUiState.Loading
            visualMediaUiState = try {
                val visualMediaList = visualMediaRepository.getTrending()
                if (visualMediaList.isEmpty()) {
                    VisualMediaUiState.Error
                } else {
                    VisualMediaUiState.Success(
                        visualMediaList,
                        ResultType.Trending,
                        genresRepository.getAllGenres().also { selectedGenres = it.toSet() }
                    )
                }
            } catch (e: Exception) {
                VisualMediaUiState.Error
            }
        }
    }

    fun addToWishList(visualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.addToWishlist(visualMedia.toSavedVisualMedia())
        }
    }

    fun addToWatchedList(visualMedia: VisualMedia) {
        viewModelScope.launch {
            savedVisualMediaRepository.addToWatchedList(visualMedia.toSavedVisualMedia())
        }
    }

    fun searchVisualMedia(query: String) {
        viewModelScope.launch {
            visualMediaUiState = VisualMediaUiState.Loading
            visualMediaUiState = try {
                val searchResults = visualMediaRepository.search(query)
                VisualMediaUiState.Success(
                    searchResults.filter { it.hasAnyGenreOf(selectedGenres) },
                    ResultType.Search,
                    genresRepository.getAllGenres(),
                    query = query
                )
            } catch (e: Exception) {
                VisualMediaUiState.Error
            }
        }
    }

    fun loadMoreResults() {
        viewModelScope.launch {
            val uiState = visualMediaUiState as VisualMediaUiState.Success
            visualMediaUiState = try {
                val results = when (uiState.resultType) {
                    ResultType.Trending -> visualMediaRepository.getMoreTrending()
                    ResultType.Search -> visualMediaRepository.getMoreSearchResults()
                }
                uiState.copy(
                    visualMediaList = uiState.visualMediaList + results.filter {
                        it.hasAnyGenreOf(selectedGenres)
                    }
                )
            } catch (e: Exception) {
                VisualMediaUiState.Error
            }
        }
    }

    fun selectedGenresChanged(genres: Set<Genre>) {
        selectedGenres = genres
        viewModelScope.launch {
            val uiState = visualMediaUiState as VisualMediaUiState.Success
            if (selectedGenres.isEmpty()) {
                visualMediaUiState = uiState.copy(visualMediaList = emptyList())
                return@launch
            }
            visualMediaUiState = try {
                when (uiState.resultType) {
                    ResultType.Trending -> {
                        var results = visualMediaRepository
                            .getTrending()
                            .filter { it.hasAnyGenreOf(genres) }
                        repeat(10) {
                            if (results.isNotEmpty()) {
                                return@repeat
                            }
                            results = visualMediaRepository.getMoreTrending()
                                .filter { it.hasAnyGenreOf(genres) }
                        }
                        uiState.copy(
                            visualMediaList = results
                        )
                    }
                    ResultType.Search -> {
                        var results = visualMediaRepository
                            .search(uiState.query!!)
                            .filter { it.hasAnyGenreOf(genres) }
                        repeat(10) {
                            if (results.isNotEmpty()) {
                                return@repeat
                            }
                            results = visualMediaRepository.getMoreSearchResults()
                                .filter { it.hasAnyGenreOf(genres) }
                        }
                        uiState.copy(
                            visualMediaList = results
                        )
                    }
                }
            } catch (e: Exception) {
                VisualMediaUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WatchedThatApplication
                val visualMediaRepository = application.container.visualMediaRepository
                val savedVisualMediaRepository =
                    application.container.savedVisualMediaRepository
                val genresRepository = application.container.genresRepository
                HomeViewModel(visualMediaRepository, savedVisualMediaRepository, genresRepository)
            }
        }
    }
}