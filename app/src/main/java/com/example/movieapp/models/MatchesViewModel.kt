package com.example.movieapp.models

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.MatchesLiveData

class MatchesViewModel : ViewModel() {
    private val matches = MatchesLiveData()

    fun getMatches() = matches.getMatches()

    fun addMatch(newMatch: Movie) = matches.addMatch(newMatch)
}