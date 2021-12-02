package com.example.movieapp.database

import androidx.lifecycle.MutableLiveData
import com.example.movieapp.models.Movie
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MatchesLiveData : MutableLiveData<MutableList<Movie>>() {
    /**
     * A reference to session's "matches" node
     */
    private var matchesReference: DatabaseReference = Database.getMatchesReference()

    /**
     * Loads a list of session's matches
     *
     * @return MatchesLiveData
     */
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
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        return this
    }

    /**
     * Add a new match to Firebase
     *
     * @param newMatch A movie that became a match
     */
    fun addMatch(newMatch: Movie) {
        val uid: String? = matchesReference.push().key
        if (uid != null) {
            matchesReference.child(uid).setValue(newMatch)
        }
    }
}