package com.example.movieapp.api.models

import com.google.gson.annotations.SerializedName

data class Person(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)
