package com.example.movieapp.models

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.MoviesBatchLiveData

class MoviesBatchViewModel : ViewModel() {
    private val batch = MoviesBatchLiveData()

    fun getBatch() = batch.getBatch()
}