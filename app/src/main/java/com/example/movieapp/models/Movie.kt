package com.example.movieapp.models

import com.google.gson.annotations.SerializedName

/**
 * Used poster sizes to compose poster URLs
 */
enum class PosterSize(val url_size: String) {
    THUMBNAIL("w92"),
    MIDDLE("w185"),
    BIG("w342")
}

/**
 * Movie object
 */
data class Movie(
    /**
     * MoviesListResponse's response deserialization
     */
    @SerializedName("id") val apiId: Long = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("original_title") val original_title: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("poster_path") val poster_path: String = "",
    @SerializedName("vote_average") val rating: Float = 0f,
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("genre_ids") val genre_ids: List<Long> = listOf()
) {
    /**
     * Firebase UID
     */
    var uid: String? = null

    /**
     * A list of users who already swiped on this movie
     */
    var swipedBy: MutableList<String> = mutableListOf()

    /**
     * A list of users who have accepted the movie
     */
    var acceptedBy: MutableList<String> = mutableListOf()

    /**
     * Returns a full poster URL
     *
     * @param size Poster sire
     */
    fun getPosterUrl(size: PosterSize) = "https://image.tmdb.org/t/p/${size.url_size}$poster_path"

    /**
     * Extracts year from release date
     */
    fun getReleaseYear() = releaseDate.substring(0, 4).toInt()
}
