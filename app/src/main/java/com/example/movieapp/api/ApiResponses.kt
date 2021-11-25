package com.example.movieapp.api

import com.example.movieapp.api.models.Genre
import com.example.movieapp.api.models.Movie
import com.example.movieapp.api.models.Person
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
