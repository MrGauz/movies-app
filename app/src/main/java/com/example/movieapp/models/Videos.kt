package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results") val results: List<Video>,
)
