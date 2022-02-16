package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

/**
 * Language for deserializing JSON in LanguagesData
 */
data class Language(
    @SerializedName("code") val code: String = "",
    @SerializedName("name") val name: String = ""
)