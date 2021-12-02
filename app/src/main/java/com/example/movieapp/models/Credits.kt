package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

/**
 * Used to deserialize MovieDetailsResponse's "credits" item
 */
data class Credits(
    @SerializedName("crew") val crew: List<Person>
)
