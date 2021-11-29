package com.example.movieapp.data

import com.example.movieapp.models.Filter
import com.example.movieapp.models.Options

object SessionData {
    var id: String? = null
    var deviceId: String? = null
    var isActive: Boolean? = null
    var startTimestamp: Long? = null
    var filter: Filter? = null
    var options: Options? = null
    var users: List<String>? = null
    var currentBatchIndex: Int = 0
}