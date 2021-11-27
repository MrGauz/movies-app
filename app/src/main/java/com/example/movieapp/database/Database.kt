package com.example.movieapp.database

import com.example.movieapp.database.models.Filter
import com.example.movieapp.database.models.Options
import com.example.movieapp.database.models.Session
import com.example.movieapp.start.JoinFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

object Database {
    private var sessionsReference: DatabaseReference
    private var genresReference: DatabaseReference
    private lateinit var sessionReference: DatabaseReference
    lateinit var sessionId: String

    init {
        val database = Firebase
            .database("https://swovieapp-default-rtdb.europe-west1.firebasedatabase.app/")
        database.setPersistenceEnabled(true)
        sessionsReference = database.getReference("sessions")
        genresReference = database.getReference("genres")
    }

    fun createNewSession(filter: Filter, options: Options): String? {
        val uid: String? = sessionsReference.push().key
        if (uid != null) {
            var session = Session(uid, System.currentTimeMillis(), true, filter, options)
            sessionsReference.child(uid).setValue(session)
            sessionId = uid
            sessionReference = sessionsReference.child(uid)
        }
        return uid
    }

    fun joinSession(inputSessionId: String, fragment: JoinFragment) {
        // Check if session with this ID exists
        sessionsReference.child(inputSessionId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value != null) {
                        // Session found
                        fragment.onSuccessfulSessionJoin()
                        sessionId = inputSessionId
                        sessionReference = sessionsReference.child(inputSessionId)
                    } else {
                        // Session not found
                        fragment.onFailedSessionJoin()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    fragment.onFailedSessionJoin()
                }
            })
    }
}