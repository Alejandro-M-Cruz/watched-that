package com.example.watchedthat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.watchedthat.model.genre.Genre
import com.example.watchedthat.ui.theme.WatchedThatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreFilters(
    genres: List<Genre>,
    onSelectedGenres: (Set<Genre>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedGenres by remember { mutableStateOf(genres.toSet()) }
    val genreFilters = genres.sortedByDescending { selectedGenres.contains(it) }
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        item {
            if (selectedGenres.containsAll(genres)) {
                IconButton(
                    onClick = {
                        selectedGenres = emptySet()
                        onSelectedGenres(selectedGenres)
                    },
                    modifier = Modifier.size(FilterChipDefaults.Height)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "Clear all filters"
                    )
                }
            } else {
                FilledTonalIconButton(
                    onClick = {
                        selectedGenres = genres.toSet()
                        onSelectedGenres(selectedGenres)
                    },
                    modifier = Modifier.size(FilterChipDefaults.Height)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Select all filters"
                    )
                }
            }
        }
        items(genreFilters, key = { it.id }) {
            GenreFilterChip(
                label = it.name,
                selected = selectedGenres.contains(it),
                onClick = {
                    selectedGenres = if (selectedGenres.contains(it)) {
                        selectedGenres - it
                    } else {
                        selectedGenres + it
                    }
                    onSelectedGenres(selectedGenres)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenreFilterChip(
    label: String,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text = label) },
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Icon that indicates that this filter is selected",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun GenreFiltersPreview() {
    WatchedThatTheme {
        GenreFilters(
            genres = listOf(
                Genre(1, "Action"),
                Genre(2, "Comedy"),
                Genre(3, "Drama"),
                Genre(4, "Horror"),
                Genre(5, "Thriller"),
                Genre(6, "Romance")
            ),
            onSelectedGenres = {}
        )
    }
}
