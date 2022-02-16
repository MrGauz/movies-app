package com.example.movieapp.models

import androidx.lifecycle.ViewModel
import com.example.movieapp.database.MoviesBatchLiveData

class MoviesBatchViewModel : ViewModel() {
    private val batch = MoviesBatchLiveData()

    /**
     * Load batch
     */
    fun getBatch() = batch.getBatch()

    /**
     * Update "swipedBy" and "acceptedBy" lists
     */
    fun setSwiped(movie: Movie, isAccepted: Boolean = false) = batch.setSwiped(movie, isAccepted)
}