package com.example.movieapp.models

/**
 * Holds an interval between 2 Int values
 */
data class Interval(
    /**
     * First value
     */
    var from: Int? = null,
    /**
     * Last value
     */
    var to: Int? = null
)