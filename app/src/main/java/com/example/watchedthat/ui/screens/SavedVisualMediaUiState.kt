package com.example.watchedthat.ui.screens

import com.example.watchedthat.model.SavedVisualMedia
import kotlinx.coroutines.flow.Flow

sealed interface SavedVisualMediaUiState {
    data class Success(val visualMediaList: Flow<List<SavedVisualMedia>>) : SavedVisualMediaUiState
    object Loading : SavedVisualMediaUiState
    object Error : SavedVisualMediaUiState
}