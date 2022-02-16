package com.example.movieapp.models

/**
 * Movies filter
 */
data class Filter(
    /**
     * List of genre's API IDs
     */
    var genres: MutableList<Long>? = null,

    /**
     * Interval for release year
     */
    var releaseYear: Interval? = null,

    /**
     * Movie's original language
     */
    var language: Language? = null,

    /**
     * Movies minimal rating from 0.0 to 10.0
     */
    var minRating: Double? = null,

    /**
     * Movie duration interval in minutes
     */
    var duration: Interval? = null
)