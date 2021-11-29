package com.example.movieapp.models

data class Filter(
    var genres: List<Long>? = null,
    var releaseYear: ReleaseYearInterval? = null,
    var country: Country? = null,
    var minRating: Double? = null,
    var duration: DurationInterval? = null
)