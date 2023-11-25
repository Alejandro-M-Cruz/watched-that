package com.example.watchedthat.ui.screens

import com.example.watchedthat.model.VisualMedia

sealed interface VisualMediaUiState {
    data class Success(val visualMediaList: List<VisualMedia>) : VisualMediaUiState
    object Loading : VisualMediaUiState
    object Error : VisualMediaUiState
}
