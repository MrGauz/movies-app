package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

enum class PosterSize(val url_size: String) {
    THUMBNAIL("w92"),
    MIDDLE("w185"),
    BIG("w342")
}

data class Movie(
    @SerializedName("id") val id: Long = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("original_title") val original_title: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("poster_path") val poster_path: String = "",
    @SerializedName("vote_average") val rating: Float = 0f,
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("genre_ids") val genre_ids: List<Long> = listOf()
) {
    var isSwiped = false
    var isAccepted = false

    fun getPosterUrl(size: PosterSize) = "https://image.tmdb.org/t/p/${size.url_size}$poster_path"
    fun getReleaseYear() = releaseDate.substring(0, 4).toInt()
}
