package com.example.movieapp.data

import com.example.movieapp.models.Interval
import com.example.movieapp.models.Filter
import com.example.movieapp.models.Options
import java.util.*

/**
 * Contains information about the session
 */
object SessionData {
    /**
     * Session's ID a.k.a. Firebase's node UID
     */
    var id: String? = null

    /**
     * Unique device ID, used to identify and distinguish users
     */
    var deviceId: String? = null

    /**
     * A flag if this user created the session
     */
    var isHost: Boolean = false

    /**
     * Timestamp of sessions beginning
     * Session begins when it's been written to Firebase
     */
    var startTimestamp: Long = Long.MAX_VALUE - 120 * 1000

    /**
     * Session's movie filter with default values
     */
    var filter = Filter(
        genres = mutableListOf(),
        releaseYear = Interval(1900, Calendar.getInstance().get(Calendar.YEAR)),
        language = LanguagesData.findByCode("en"),
        minRating = 4.2,
        duration = Interval(0, 300)
    )

    /**
     * Session's options with default values
     */
    var options = Options(
        matchPercentage = 100,
        joinTimer = 120
    )

    /**
     * A list of active session users' device IDs
     */
    var users: MutableList<String>? = null

    /**
     * Index of the movies batch currently shown to user
     */
    var currentBatchIndex: Int = 0

    /**
     * A list of UIDs for all batches loaded to Firebase
     */
    var batchUids: MutableList<String> = mutableListOf()

    /**
     * A method to check if join timer is over yet
     */
    fun isJoinTimerOver() = startTimestamp + options.joinTimer * 1000 < System.currentTimeMillis()
}