package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

/**
 * A list of videos associated with a movie
 * Used to deserialize MovieDetailsResponse
 */
data class Videos(
    @SerializedName("results") val results: List<Video>,
)
