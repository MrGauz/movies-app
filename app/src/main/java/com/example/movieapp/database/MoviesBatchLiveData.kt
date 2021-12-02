package com.example.movieapp.database

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.SessionData
import com.example.movieapp.models.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MoviesBatchLiveData : MutableLiveData<MutableList<Movie>>() {
    /**
     * A reference to session's "movies" node
     */
    private var moviesReference: DatabaseReference = Database.getMoviesReference()

    fun getBatch(): MoviesBatchLiveData {
        moviesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Load movies from the database
                val tmpMoviesList = mutableListOf<Movie>()
                if (snapshot.children.count() >= SessionData.currentBatchIndex + 1) {
                    snapshot.children.elementAt(SessionData.currentBatchIndex).children.forEach {
                        if (it != null) {
                            tmpMoviesList.add(it.getValue<Movie>()!!)
                        }
                    }
                }

                // Update data in LiveData
                value = tmpMoviesList
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return this
    }

    fun setSwiped(movie: Movie, isAccepted: Boolean = false) {
        movie.swipedBy.add(SessionData.deviceId!!)
        moviesReference.child(SessionData.batchUids[SessionData.currentBatchIndex])
            .child(movie.uid!!).child("swipedBy").setValue(movie.swipedBy)

        if (isAccepted) {
            movie.acceptedBy.add(SessionData.deviceId!!)
            moviesReference
                .child(SessionData.batchUids[SessionData.currentBatchIndex]).child(movie.uid!!)
                .child("acceptedBy").setValue(movie.acceptedBy)
        }
    }
}