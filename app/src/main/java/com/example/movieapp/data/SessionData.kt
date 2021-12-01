package com.example.movieapp.data

import com.example.movieapp.models.DurationInterval
import com.example.movieapp.models.Filter
import com.example.movieapp.models.Options
import com.example.movieapp.models.ReleaseYearInterval
import java.util.*

object SessionData {
    var id: String? = null
    var deviceId: String? = null
    var isHost: Boolean = false
    var isActive: Boolean? = null
    var startTimestamp: Long = Long.MAX_VALUE - 120 * 1000
    var filter = Filter(
        genres = mutableListOf(),
        releaseYear = ReleaseYearInterval(1900, Calendar.getInstance().get(Calendar.YEAR)),
        language = LanguagesData.findByCode("en"),
        minRating = 4.2,
        duration = DurationInterval(0, 300)
    )
    var options = Options(
        matchPercentage = 100,
        joinTimer = 120
    )
    var users: MutableList<String>? = null
    var currentBatchIndex: Int = 0
    var batchUids: MutableList<String> = mutableListOf()

    fun isJoinTimerOver() = startTimestamp + options.joinTimer * 1000 < System.currentTimeMillis()
}