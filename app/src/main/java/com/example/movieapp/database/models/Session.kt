package com.example.movieapp.database.models

data class Session(
    val id: Int,
    val start_timestamp: Int,
    var filter: Filter,
    var options: Options,
    var movies: List<List<Movie>>,
    var matches: List<Movie>
)