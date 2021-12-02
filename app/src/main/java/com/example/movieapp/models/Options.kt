package com.example.movieapp.models

/**
 * Session's options object
 */
data class Options(
    /**
     * Percentage of active users it takes to accept a movie for it to become a match
     * Takes values from 0 to 100
     */
    var matchPercentage: Int = 100,
    /**
     * Defines how long new users can join the session in seconds
     */
    var joinTimer: Int = 120
)