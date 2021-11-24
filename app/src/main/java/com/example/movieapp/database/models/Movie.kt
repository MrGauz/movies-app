package com.example.movieapp.database.models

data class Movie(
    val api_id: Int,
    val title: String,
    val release_year: Int,
    val genres: List<Int>,
    val director: Director,
    val poster_url: String
)