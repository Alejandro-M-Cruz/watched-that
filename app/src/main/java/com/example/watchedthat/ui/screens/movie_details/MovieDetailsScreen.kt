package com.example.watchedthat.ui.screens.movie_details

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
import com.example.watchedthat.model.details.MovieDetails
import com.example.watchedthat.model.visualmedia.SavedVisualMedia
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
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel) {
    when(viewModel.movieDetailsUiState) {
        is MovieDetailsUiState.Success -> {
            val uiState = viewModel.movieDetailsUiState as MovieDetailsUiState.Success
            MovieDetailsColumn(
                movieDetails = uiState.movieDetails,
                onAddToWishlist = viewModel::addToWishlist,
                onAddToWatchedList = viewModel::addToWatchedList,
                savedVisualMedia = uiState.savedVisualMedia,
                modifier = Modifier.fillMaxWidth()
            )
        }
        is MovieDetailsUiState.Error -> {
            ErrorScreen(
                errorMessage = "Error loading movie details. " +
                    "Please turn on your internet connection and try again.",
                retryAction = viewModel::loadUiState
            )
        }
        is MovieDetailsUiState.Loading -> {
            LoadingScreen()
        }
    }
}

@Composable
fun MovieDetailsColumn(
    movieDetails: MovieDetails,
    savedVisualMedia: Flow<SavedVisualMedia?>,
    onAddToWishlist: () -> Unit,
    onAddToWatchedList: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        if (movieDetails.trailerPath != null) {
            item {
                TrailerVideo(
                    trailerPath = movieDetails.trailerPath!!,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
                ExternalLink(
                    text = "Watch trailer on Youtube",
                    url = movieDetails.trailerUrl!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 16.dp)
                        .fillMaxWidth()
                )
            }
        }
        item {
            VisualMediaHeader(
                title = movieDetails.title,
                rating = movieDetails.rating,
                ratingCount = movieDetails.ratingCount,
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
                    text = movieDetails.mediaType.displayName,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                val savedMovieState by savedVisualMedia.collectAsState(initial = "loading")
                val savedMovie = savedMovieState ?: movieDetails.toSavedVisualMedia()
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
            TextDetail("Original title: ", movieDetails.originalTitle),
            TextDetail("Original language: ", movieDetails.originalLanguage),
            TextDetail("Genres: ", movieDetails.formattedGenres),
            TextDetail("Release date: ", movieDetails.releaseDate),
            TextDetail("Runtime: ", "${movieDetails.runtimeInMinutes} minutes"),
            TextDetail("Budget: ", movieDetails.formattedBudget),
            TextDetail("Revenue: ", movieDetails.formattedRevenue),
            TextDetail("Overview: ", movieDetails.overview),
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
        if (!movieDetails.websiteUrl.isNullOrBlank()) {
            item {
                ExternalLink(
                    text = "Website",
                    url = movieDetails.websiteUrl,
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
                imageUrl = movieDetails.posterUrl,
                title = movieDetails.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}
