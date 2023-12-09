package com.example.watchedthat.ui.screens

import com.example.watchedthat.model.genre.Genre
import com.example.watchedthat.model.visual_media.SavedVisualMedia
import kotlinx.coroutines.flow.Flow

sealed interface SavedVisualMediaUiState {
    data class Success(
        val visualMediaList: Flow<List<SavedVisualMedia>>,
        val genres: Flow<List<Genre>>
    ) : SavedVisualMediaUiState
    object Loading : SavedVisualMediaUiState
    object Error : SavedVisualMediaUiState
}