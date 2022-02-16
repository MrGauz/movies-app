package com.example.movieapp.models

import androidx.lifecycle.ViewModel
import com.example.movieapp.database.MatchesLiveData

class MatchesViewModel : ViewModel() {
    private val matches = MatchesLiveData()

    /**
     * Load matches
     */
    fun getMatches() = matches.getMatches()

    /**
     * Add a new match
     */
    fun addMatch(newMatch: Movie) = matches.addMatch(newMatch)
}