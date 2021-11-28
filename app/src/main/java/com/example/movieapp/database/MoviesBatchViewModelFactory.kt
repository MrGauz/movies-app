package com.example.movieapp.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.models.MoviesBatchViewModel

class MoviesBatchViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesBatchViewModel::class.java)) {
            return MoviesBatchViewModel() as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }
}