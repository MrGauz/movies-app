package com.example.movieapp.database.models

data class Filter(
    var genres: List<Int>?,
    var release_year: ReleaseYearInterval?,
    var director: Director?,
    var min_rating: Double?,
    var duration: DurationInterval?
)