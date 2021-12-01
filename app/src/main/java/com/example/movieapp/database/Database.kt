package com.example.movieapp.database

import com.example.movieapp.data.SessionData
import com.example.movieapp.models.*
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

    init {
        val database = Firebase
            .database("https://swovieapp-default-rtdb.europe-west1.firebasedatabase.app/")
        database.setPersistenceEnabled(true)
        sessionsReference = database.getReference("sessions")
    }

    fun createNewSession(): String? {
        val uid: String? = sessionsReference.push().key
        if (uid != null) {
            // Create session
            val session = Session(
                uid,
                System.currentTimeMillis(),
                true,
                SessionData.filter,
                SessionData.options
            )
            sessionsReference.child(uid).setValue(session)
            SessionData.id = uid
            sessionReference = sessionsReference.child(uid)
            // Add user
            val newUser = sessionReference.child("users").push()
            newUser.setValue(SessionData.deviceId)
            // Save startTimestamp
            SessionData.startTimestamp = session.startTimestamp
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

                    // Check if session is still active
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
                    SessionData.id = inputSessionId
                    sessionReference = sessionsReference.child(inputSessionId)
                    // Add user
                    val newUser = sessionReference.child("users").push()
                    newUser.setValue(SessionData.deviceId)
                    // UI callback to continue
                    fragment.onSuccessfulSessionJoin()
                }

                override fun onCancelled(error: DatabaseError) {
                    fragment.onFailedSessionJoin()
                }
            })
    }

    fun saveNewMoviesBatch(batch: List<Movie>) {
        val batchUid = sessionReference.child("movies").push().key
        if (batchUid != null) {
            // Save new batch uid
            SessionData.batchUids.add(batchUid)
            sessionReference.child("batchUids").setValue(SessionData.batchUids)

            // Save loaded movies to batch
            val batchReference = sessionReference.child("movies").child(batchUid)
            for (movie in batch) {
                val uid = batchReference.push().key
                if (uid != null) {
                    movie.uid = uid
                    batchReference.child(uid).setValue(movie)
                }
            }
        }
    }

    fun loadNextMoviesBatch() {
        TODO()
    }

    fun loadSessionData() {
        // Load isActive
        sessionReference.child("active").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                SessionData.isActive = snapshot.getValue<Boolean>()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // Load filter (only guests)
        if (!SessionData.isHost) {
            sessionReference.child("filter")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.getValue<Filter>() != null) {
                            SessionData.filter = snapshot.getValue<Filter>()!!
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }

        // Load options (only guests)
        if (!SessionData.isHost) {
            sessionReference.child("options")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.getValue<Options>() != null) {
                            SessionData.options = snapshot.getValue<Options>()!!
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }

        // Load batches uids
        sessionReference.child("batchUids").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tmpBatchesUidsList = mutableListOf<String>()
                snapshot.children.forEach {
                    if (it != null) {
                        tmpBatchesUidsList.add(it.getValue<String>()!!)
                    }
                }

                SessionData.batchUids = tmpBatchesUidsList
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // Load active users
        sessionReference.child("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tmpUsersList = mutableListOf<String>()
                snapshot.children.forEach {
                    if (it != null) {
                        tmpUsersList.add(it.getValue<String>()!!)
                    }
                }

                SessionData.users = tmpUsersList
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun leaveSession() {
        if (SessionData.users != null) {
            sessionReference.child("users")
                .setValue(SessionData.users!!.filter { u -> u != SessionData.deviceId })
        }
    }

    fun getMoviesReference() = sessionReference.child("movies")

    fun getMatchesReference() = sessionReference.child("matches")
}