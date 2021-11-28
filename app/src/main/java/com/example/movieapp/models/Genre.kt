package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)