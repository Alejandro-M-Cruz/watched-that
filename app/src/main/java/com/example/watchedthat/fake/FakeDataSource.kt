package com.example.watchedthat.fake

import com.example.watchedthat.model.Movie
import com.example.watchedthat.model.TvShow
import com.example.watchedthat.model.Genre

object FakeDataSource {
    val genre1 = Genre(id = 1, name = "Genre1")
    val genre2 = Genre(id = 2, name = "Genre2")
    val genreList = listOf(genre1, genre2)
    val movie1 = Movie(
        id = 1,
        title = "Title",
        posterPath = "posterPath",
        backdropPath = "backdropPath",
        releaseDate = "releaseDate",
        genreIds = listOf(1, 2),
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
        genreIds = listOf(1, 2),
        rating = 1f,
        ratingCount = 1,
        popularity = 1f
    )
    val tvShow2 = tvShow1.copy(id = 2)
    val tvShowList = listOf(tvShow1, tvShow2)
    val visualMediaList = movieList + tvShowList
}