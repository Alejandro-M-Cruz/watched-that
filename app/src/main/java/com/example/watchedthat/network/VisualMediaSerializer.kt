package com.example.watchedthat.network

import com.example.watchedthat.model.MediaType
import com.example.watchedthat.model.details.MovieDetails
import com.example.watchedthat.model.details.TvShowDetails
import com.example.watchedthat.model.visual_media.Movie
import com.example.watchedthat.model.visual_media.TvShow
import com.example.watchedthat.model.visual_media.VisualMedia
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class VisualMediaSerializer : JsonContentPolymorphicSerializer<VisualMedia>(VisualMedia::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<VisualMedia> {
        val jsonObject = element.jsonObject
        return when {
            "media_type" in jsonObject -> when (jsonObject["media_type"]!!.jsonPrimitive.content) {
                "movie" -> Movie.serializer()
                "tv" -> TvShow.serializer()
                else -> UnknownVisualMedia.serializer()
            }
            "title" in jsonObject -> Movie.serializer()
            "name" in jsonObject -> TvShow.serializer()
            "episode_run_time" in jsonObject -> TvShowDetails.serializer()
            "runtime" in jsonObject -> MovieDetails.serializer()
            else -> UnknownVisualMedia.serializer()
        }
    }
}

@Serializable
class UnknownVisualMedia : VisualMedia {
    override val id: Int
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val title: String
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val releaseDate: String
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val rating: Float
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val ratingCount: Int
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val popularity: Float
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val posterPath: String
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val backdropPath: String
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")
    override val genreIds: List<Int>
        get() = throw UnsupportedOperationException("Unknown VisualMedia type")

    override val mediaType = MediaType.UNKNOWN
}



