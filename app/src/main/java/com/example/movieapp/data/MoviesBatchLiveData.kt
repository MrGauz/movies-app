package com.example.movieapp.data

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.database.Database
import com.example.movieapp.models.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MoviesBatchLiveData : MutableLiveData<MutableList<Movie>>() {
    private var reference = Database.getMoviesReference()
    var currentIndex = 0

    fun getBatch(): MoviesBatchLiveData {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Load movies from the database
                val tmpMoviesList = mutableListOf<Movie>()
                snapshot.children.elementAt(currentIndex).children.forEach {
                    if (it != null) {
                        tmpMoviesList.add(it.getValue<Movie>()!!)
                    }
                }

                // Update data in LiveData
                value = tmpMoviesList
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return this
    }
}