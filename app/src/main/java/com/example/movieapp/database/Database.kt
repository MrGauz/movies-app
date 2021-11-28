package com.example.movieapp.database

import com.example.movieapp.models.Filter
import com.example.movieapp.models.Movie
import com.example.movieapp.models.Options
import com.example.movieapp.models.Session
import com.example.movieapp.start.JoinFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

object Database {
    private var sessionsReference: DatabaseReference
    private lateinit var sessionReference: DatabaseReference
    lateinit var sessionId: String
    lateinit var deviceId: String

    init {
        val database = Firebase
            .database("https://swovieapp-default-rtdb.europe-west1.firebasedatabase.app/")
        database.setPersistenceEnabled(true)
        sessionsReference = database.getReference("sessions")
    }

    fun createNewSession(filter: Filter, options: Options): String? {
        val uid: String? = sessionsReference.push().key
        if (uid != null) {
            // Create session
            val session = Session(uid, System.currentTimeMillis(), true, filter, options)
            sessionsReference.child(uid).setValue(session)
            sessionId = uid
            sessionReference = sessionsReference.child(uid)
            // Add user
            val newUser = sessionReference.child("users").push()
            newUser.setValue(deviceId)
        }
        return uid
    }

    fun joinSession(inputSessionId: String, fragment: JoinFragment) {
        // Check if session with this ID exists
        sessionsReference.child(inputSessionId).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value == null) {
                        // Session not found
                        fragment.onFailedSessionJoin("Session not found")
                        return
                    }

                    val session = snapshot.getValue<Session>()
                    if (session == null) {
                        fragment.onFailedSessionJoin()
                        return
                    }

                    if (!session.isActive) {
                        fragment.onFailedSessionJoin("This session is over")
                        return
                    }

                    // Check if join time is over
                    if (session.startTimestamp + session.options.joinTimer * 1000 < System.currentTimeMillis()) {
                        fragment.onFailedSessionJoin("Time to join the session is over")
                        return
                    }

                    // Save session data
                    sessionId = inputSessionId
                    sessionReference = sessionsReference.child(inputSessionId)
                    // Add user
                    val newUser = sessionReference.child("users").push()
                    newUser.setValue(deviceId)
                    // UI callback to continue
                    fragment.onSuccessfulSessionJoin()
                }

                override fun onCancelled(error: DatabaseError) {
                    fragment.onFailedSessionJoin()
                }
            })
    }

    fun saveNewMoviesBatch(batch: List<Movie>) {
        val uid = sessionReference.child("movies").push().key
        if (uid != null) {
            sessionReference.child("movies").child(uid).setValue(batch)
        }
    }

    fun loadNextMoviesBatch() {
        TODO()
    }
}