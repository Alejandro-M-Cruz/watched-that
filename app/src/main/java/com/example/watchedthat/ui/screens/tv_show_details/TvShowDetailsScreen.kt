package com.example.watchedthat.ui.screens.tv_show_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchedthat.model.details.TvShowDetails
import com.example.watchedthat.model.visual_media.SavedVisualMedia
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.ExternalLink
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.TextDetail
import com.example.watchedthat.ui.components.TextDetailColumn
import com.example.watchedthat.ui.components.TrailerVideo
import com.example.watchedthat.ui.components.VisualMediaHeader
import com.example.watchedthat.ui.components.VisualMediaImage
import com.example.watchedthat.ui.components.WatchedButton
import com.example.watchedthat.ui.components.WishlistButton
import kotlinx.coroutines.flow.Flow

@Composable
fun TvShowDetailsScreen(viewModel: TvShowDetailsViewModel) {
    when (viewModel.tvShowDetailsUiState) {
        is TvShowDetailsUiState.Success -> {
            val tvShowDetails = (viewModel.tvShowDetailsUiState as TvShowDetailsUiState.Success).tvShowDetails
            val savedVisualMedia = (viewModel.tvShowDetailsUiState as TvShowDetailsUiState.Success).savedVisualMedia
            TvShowDetailsColumn(
                tvShowDetails = tvShowDetails,
                savedVisualMedia = savedVisualMedia,
                onAddToWishlist = { viewModel.addToWishlist() },
                onAddToWatchedList = { viewModel.addToWatchedList() },
                modifier = Modifier.fillMaxWidth()
            )
        }
        is TvShowDetailsUiState.Error -> {
            ErrorScreen(
                errorMessage = "Error loading TV show details. " +
                    "Please turn on your internet connection and try again.",
                retryAction = viewModel::loadUiState
            )
        }
        is TvShowDetailsUiState.Loading -> LoadingScreen()
    }
}

@Composable 
fun TvShowDetailsColumn(
    tvShowDetails: TvShowDetails,
    savedVisualMedia: Flow<SavedVisualMedia?>,
    onAddToWishlist: () -> Unit,
    onAddToWatchedList: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        if (tvShowDetails.trailerPath != null) {
            item {
                TrailerVideo(
                    trailerPath = tvShowDetails.trailerPath!!,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
                ExternalLink(
                    text = "Watch trailer on Youtube",
                    url = tvShowDetails.trailerUrl!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
        item {
            VisualMediaHeader(
                title = tvShowDetails.title,
                rating = tvShowDetails.rating,
                ratingCount = tvShowDetails.ratingCount,
                titleFontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 4.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = tvShowDetails.mediaType.displayName,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                val savedMovieState by savedVisualMedia.collectAsState(initial = "loading")
                val savedMovie = savedMovieState ?: tvShowDetails.toSavedVisualMedia()
                if (savedMovieState != "loading") {
                    WishlistButton(
                        savedVisualMedia = savedMovie as SavedVisualMedia,
                        onClick = { onAddToWishlist() }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    WatchedButton(
                        savedVisualMedia = savedMovie,
                        onClick = { onAddToWatchedList() }
                    )
                }
            }
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 8.dp, end = 8.dp))
        }
        items(arrayOf(
            TextDetail("Original title: ", tvShowDetails.originalTitle),
            TextDetail("Original language: ", tvShowDetails.originalLanguage),
            TextDetail("Genres: ", tvShowDetails.formattedGenres),
            TextDetail("First air date: ", tvShowDetails.releaseDate),
            TextDetail("Last air date: ", tvShowDetails.lastAirDate ?: "Unknown"),
            TextDetail("Number of seasons: ", tvShowDetails.numberOfSeasons.toString()),
            TextDetail("Number of episodes: ", tvShowDetails.numberOfEpisodes.toString()),
            TextDetail("Episode runtimes:", tvShowDetails.formattedEpisodeRuntimes),
            TextDetail("In production: ", tvShowDetails.isInProduction),
            TextDetail("Overview: ", tvShowDetails.overview),
        )) {
            TextDetailColumn(
                it.label,
                it.value,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Divider(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 8.dp))
        }
        if (!tvShowDetails.websiteUrl.isNullOrBlank()) {
            item {
                ExternalLink(
                    text = "Website",
                    url = tvShowDetails.websiteUrl,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    fontSize = 18.sp
                )
                Divider(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 8.dp))
            }
        }
        item {
            VisualMediaImage(
                imageUrl = tvShowDetails.posterUrl,
                title = tvShowDetails.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

