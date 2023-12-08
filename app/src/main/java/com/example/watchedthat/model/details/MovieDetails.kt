package com.example.watchedthat.model.details

import com.example.watchedthat.model.MediaType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Serializable
data class MovieDetails(
    override val id: Int,
    override val title: String,
    @SerialName("release_date")
    override val releaseDate: String,
    @SerialName("vote_average")
    override val rating: Float,
    @SerialName("vote_count")
    override val ratingCount: Int,
    override val popularity: Float,
    @SerialName("poster_path")
    override val posterPath: String? = null,
    @SerialName("backdrop_path")
    override val backdropPath: String? = null,
    @SerialName("original_title")
    override val originalTitle: String,
    val budget: Long,
    val revenue: Long,
    override val overview: String,
    @SerialName("original_language")
    override val originalLanguageCode: String,
    @SerialName("homepage")
    override val websiteUrl: String?,
    override val videos: VideoResults,
    @SerialName("runtime")
    val runtimeInMinutes: Int,
) : VisualMediaDetails {
    override val mediaType = MediaType.MOVIE

    val formattedBudget: String
        get() = if (revenue == 0L) "Unknown" else formatAmount(budget)

    val formattedRevenue: String
        get() = if (revenue == 0L) "Unknown" else formatAmount(revenue)

    private fun formatAmount(amount: Long): String {
        val locale = Locale("en", "US")
        val currencyFormatter = NumberFormat.getCurrencyInstance(locale)
        currencyFormatter.maximumFractionDigits = 0
        return currencyFormatter.format(amount)
    }
}
