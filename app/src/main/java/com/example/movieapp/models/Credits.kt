package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("crew") val crew: List<Person>
)
