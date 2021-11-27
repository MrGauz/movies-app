package com.example.movieapp.database.models

data class Filter(
    var genres: List<Int>?,
    var releaseYear: ReleaseYearInterval?,
    var director: Director?,
    var minRating: Double?,
    var duration: DurationInterval?
)