package com.example.movieapp.database.models

data class Session(
    var id: String = "",
    var startTimestamp: Long = 0,
    var isActive: Boolean = false,
    var filter: Filter = Filter(),
    var options: Options = Options(),
) {
    var matches: List<Movie>? = null
    var movies: List<List<Movie>>? = null
}