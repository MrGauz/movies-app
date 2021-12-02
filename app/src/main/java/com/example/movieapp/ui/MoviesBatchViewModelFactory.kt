package com.example.movieapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.models.MoviesBatchViewModel

/**
 * Factory for loading movie batches
 */
class MoviesBatchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesBatchViewModel::class.java)) {
            return MoviesBatchViewModel() as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }
}