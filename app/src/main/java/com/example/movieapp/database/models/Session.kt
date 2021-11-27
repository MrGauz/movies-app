package com.example.movieapp.database.models

data class Session(
    val id: String?,
    val startTimestamp: Long,
    var isActive: Boolean,
    var filter: Filter,
    var options: Options,
) {
    var matches: List<Movie>? = null
    var movies: List<List<Movie>>? = null
}