package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

/**
 * A video associated with a movie
 * Used to deserialize MovieDetailsResponse
 */
data class Video(
    @SerializedName("name") val name: String,
    @SerializedName("key") val key: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String,
)
