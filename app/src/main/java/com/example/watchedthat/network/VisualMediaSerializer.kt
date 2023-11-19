package com.example.watchedthat.network

import com.example.watchedthat.model.Movie
import com.example.watchedthat.model.TvShow
import com.example.watchedthat.model.VisualMedia
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationException
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
                else -> throw IllegalArgumentException("Unknown media type")
            }
            "title" in jsonObject -> Movie.serializer()
            "name" in jsonObject -> TvShow.serializer()
            else -> throw SerializationException("Could not determine media type")
        }
    }

}