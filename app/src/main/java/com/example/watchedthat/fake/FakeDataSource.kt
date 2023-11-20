package com.example.watchedthat.fake

import com.example.watchedthat.model.Movie
import com.example.watchedthat.model.TvShow

object FakeDataSource {
    val movie1 = Movie(
        id = 1,
        title = "Title",
        posterPath = "posterPath",
        backdropPath = "backdropPath",
        releaseDate = "releaseDate",
        rating = 1f,
        ratingCount = 1,
        popularity = 1f
    )
    val movie2 = movie1.copy(id = 2)
    val movieList = listOf(movie1, movie2)
    val tvShow1 = TvShow(
        id = 1,
        title = "Title",
        posterPath = "posterPath",
        backdropPath = "backdropPath",
        releaseDate = "releaseDate",
        rating = 1f,
        ratingCount = 1,
        popularity = 1f
    )
    val tvShow2 = tvShow1.copy(id = 2)
    val tvShowList = listOf(tvShow1, tvShow2)
    val visualMediaList = movieList + tvShowList
    val savedVisualMedia1 = movie1.toSavedVisualMedia()
    val savedVisualMedia2 = tvShow1.toSavedVisualMedia()
    val savedVisualMediaList = listOf(savedVisualMedia1, savedVisualMedia2)
}