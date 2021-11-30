package com.example.movieapp.data

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.database.Database
import com.example.movieapp.models.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MatchesLiveData : MutableLiveData<MutableList<Movie>>() {
    private var matchesReference: DatabaseReference = Database.getMatchesReference()

    fun getMatches(): MatchesLiveData {
        matchesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Load matches from the database
                val tmpMoviesList = mutableListOf<Movie>()
                snapshot.children.forEach {
                    if (it != null) {
                        tmpMoviesList.add(it.getValue<Movie>()!!)
                    }
                }

                // Update data in LiveData
                value = tmpMoviesList

                if (tmpMoviesList.size >= 3) {
                    // Limit reached
                    // TODO - Carlos: navigate to matches screen
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return this
    }

    fun addMatch(newMatch: Movie) {
        val uid: String? = matchesReference.push().key
        if (uid != null) {
            matchesReference.child(uid).setValue(newMatch)
        }
    }
}