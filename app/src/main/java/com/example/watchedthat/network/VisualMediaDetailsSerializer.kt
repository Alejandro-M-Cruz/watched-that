package com.example.watchedthat.network

import com.example.watchedthat.model.details.MovieDetails
import com.example.watchedthat.model.details.TvShowDetails
import com.example.watchedthat.model.details.VideoResults
import com.example.watchedthat.model.details.VisualMediaDetails
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

class VisualMediaDetailsSerializer : JsonContentPolymorphicSerializer<VisualMediaDetails>(VisualMediaDetails::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<VisualMediaDetails> {
        val jsonObject = element.jsonObject
        return when {
            "runtime" in jsonObject -> MovieDetails.serializer()
            "episode_run_time" in jsonObject -> TvShowDetails.serializer()
            else -> UnknownVisualMediaDetails.serializer()
        }
    }
}

@Serializable
class UnknownVisualMediaDetails : VisualMediaDetails {
    override val id: Int
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val title: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val releaseDate: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val rating: Float
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val ratingCount: Int
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val popularity: Float
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val posterPath: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val backdropPath: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val originalTitle: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val overview: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val originalLanguage: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val website: String
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")
    override val videos: VideoResults
        get() = throw UnsupportedOperationException("Unknown VisualMediaDetails type")

    override val mediaType = throw UnsupportedOperationException("Unknown media type")
}
