package com.example.watchedthat.fake

import com.example.watchedthat.model.details.MovieDetails
import com.example.watchedthat.model.details.VideoResults
import com.example.watchedthat.model.visualmedia.Movie
import com.example.watchedthat.model.visualmedia.TvShow

object FakeDataSource {
    val movie1 = Movie(
        id = 1,
        title = "title1",
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
        title = "title2",
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

    val movieWithImages = movie1.copy(
        title = "This is a really long example title that should be wrapped",
        ratingCount = 10000000,
        posterPath = "/NNxYkU70HPurnNCSiCjYAmacwm.jpg",
        backdropPath = "/6KErczPBROQty7QoIsaa6wJYXZi.jpg"
    )
    val tvShowWithImages = tvShow1.copy(
        ratingCount = 10000000,
        posterPath = "/1XS1oqL89opfnbLl8WnZY1O1uJx.jpg",
        backdropPath = "/2OMB0ynKlyIenMJWI2Dy9IWT4c.jpg"
    )
    val visualMediaWithImages = listOf(movieWithImages, tvShowWithImages)

    val movieDetails1 = MovieDetails(
        id = 1,
        title = "title1",
        posterPath = "posterPath",
        backdropPath = "backdropPath",
        releaseDate = "releaseDate",
        rating = 1f,
        ratingCount = 1,
        popularity = 1f,
        overview = "overview",
        budget = 100000,
        revenue = 100000,
        runtimeInMinutes = 100,
        videos = VideoResults(
            results = emptyList()
        ),
        originalLanguageCode = "en",
        originalTitle= "originalTitle",
        websiteUrl = "website"
    )
}