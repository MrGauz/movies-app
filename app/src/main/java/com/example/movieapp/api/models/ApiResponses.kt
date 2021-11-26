package com.example.movieapp.api.models

import com.google.gson.annotations.SerializedName

class ApiResponses()

data class MoviesListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int,
    @SerializedName("total_results") val total_results: Int
)

data class GenresListResponse(
    @SerializedName("genres") val genres: List<Genre>,
)

data class PeopleListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val people: List<Person>,
    @SerializedName("total_pages") val pages: Int,
    @SerializedName("total_results") val total_results: Int
)

data class MovieDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val poster_path: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("runtime") val duration: Int,
    @SerializedName("videos") val videos: Videos,
    // TODO: director, screenplay by etc
)