package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.models.MatchesViewModel

/**
 * Factory for loading matches
 */
class MatchesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchesViewModel::class.java)) {
            return MatchesViewModel() as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }
}