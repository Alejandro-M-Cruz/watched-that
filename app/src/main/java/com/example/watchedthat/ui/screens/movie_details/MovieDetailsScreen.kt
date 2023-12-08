package com.example.watchedthat.ui.screens.movie_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchedthat.fake.FakeDataSource
import com.example.watchedthat.model.details.MovieDetails
import com.example.watchedthat.ui.components.ErrorScreen
import com.example.watchedthat.ui.components.LoadingScreen
import com.example.watchedthat.ui.components.VisualMediaHeader
import com.example.watchedthat.ui.components.VisualMediaImage

@Composable
fun MovieDetailsScreen(viewModel: MovieDetailsViewModel) {
    when(viewModel.movieDetailsUiState) {
        is MovieDetailsUiState.Success -> {
            val uiState = viewModel.movieDetailsUiState as MovieDetailsUiState.Success
            MovieDetailsColumn(
                movieDetails = uiState.movieDetails,
                onAddToWishlist = viewModel::addToWishlist,
                onAddToWatchedList = viewModel::addToWatchedList,
                modifier = Modifier.fillMaxWidth()
            )
        }
        is MovieDetailsUiState.Error -> {
            ErrorScreen(
                errorMessage = "Error loading movie details. " +
                    "Please turn on your internet connection and try again.",
                retryAction = viewModel::loadMovieDetails
            )
        }
        is MovieDetailsUiState.Loading -> {
            LoadingScreen()
        }
    }
}


data class TextDetail(val label: String, val value: String)

@Composable
fun MovieDetailsColumn(
    movieDetails: MovieDetails,
    onAddToWishlist: () -> Unit,
    onAddToWatchedList: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        VisualMediaImage(
            imageUrl = movieDetails.backdropUrl,
            Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            VisualMediaHeader(
                title = movieDetails.title,
                rating = movieDetails.rating,
                ratingCount = movieDetails.ratingCount,
                titleFontSize = 24.sp,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                item {
                    Divider(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp))
                }
                items(arrayOf(
                    TextDetail("Original title: ", movieDetails.originalTitle),
                    TextDetail("Original language: ", movieDetails.originalLanguage),
                    TextDetail("Release date: ", movieDetails.releaseDate),
                    TextDetail("Runtime: ", "${movieDetails.runtimeInMinutes} minutes"),
                    TextDetail("Budget: ", "$${movieDetails.formattedBudget}"),
                    TextDetail("Revenue: ", "$${movieDetails.formattedRevenue}"),
                    TextDetail("Popularity: ", "${movieDetails.popularity}"),
                    TextDetail("Overview: ", movieDetails.overview),
                )) {
                    TextDetailColumn(it.label, it.value)
                    Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp))
                }
                if (movieDetails.websiteUrl != null) {
                    item {
                        WebsiteLink(websiteUrl = movieDetails.websiteUrl)
                        Divider(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp))
                    }
                }

            }
        }
    }
}

@Composable
fun TextDetailColumn(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
fun WebsiteLink(websiteUrl: String, modifier: Modifier = Modifier) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )) {
                addStringAnnotation(
                    tag = "Website",
                    annotation = websiteUrl,
                    start = 0,
                    end = websiteUrl.length
                )
            }
        },
        fontSize = 16.sp,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MovieDetailsColumnPreview() {
    MovieDetailsColumn(
        movieDetails = FakeDataSource.movieDetails1,
        onAddToWishlist = {},
        onAddToWatchedList = {}
    )
}

