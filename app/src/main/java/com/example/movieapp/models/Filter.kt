package com.example.movieapp.models

data class Filter(
    var genres: MutableList<Long>? = null,
    var releaseYear: ReleaseYearInterval? = null,
    var country: String? = null,
    var minRating: Double? = null,
    var duration: DurationInterval? = null
)