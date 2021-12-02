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
    /**
     * Root reference
     */
    private var sessionsReference: DatabaseReference

    /**
     * Reference to current session
     */
    private lateinit var sessionReference: DatabaseReference

    init {
        val database = Firebase
            .database("https://swovieapp-default-rtdb.europe-west1.firebasedatabase.app/")
        database.setPersistenceEnabled(true)
        sessionsReference = database.getReference("sessions")
    }

    /**
     * Creates a new session in Firebase
     *
     * @return New session's UID
     */
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
            // Add user to the active users list
            val newUser = sessionReference.child("users").push()
            newUser.setValue(SessionData.deviceId)
            // Save startTimestamp
            SessionData.startTimestamp = session.startTimestamp
        }
        return uid
    }

    /**
     * Join a session and loads data about it if
     *   - session with passed session ID exists
     *   - time to join is not over yet
     *
     *   @param inputSessionId session ID passed in JoinFragment or per join link
     *   @param fragment JoinFragment instance that called this method for UI update callback
     */
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

                    // Try to deserialize to Session object
                    val session = snapshot.getValue<Session>()
                    if (session == null) {
                        fragment.onFailedSessionJoin()
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

                    // Add user to active users list
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

    /**
     * Saves passed list of movies as a new batch in Firebase
     *
     * @param batch A new batch of movies
     */
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

    /**
     * Clear the "matches" node in Firebase
     */
    fun clearMatches() {
        sessionReference.child("matches").setValue(null)
    }

    /**
     * Load session data and add onDataChange listeners to keep it synced
     */
    fun loadSessionData() {
        // Load start timestamp once (only guests)
        if (!SessionData.isHost) {
            sessionReference.child("startTimestamp")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.getValue<Long>() != null) {
                            SessionData.startTimestamp = snapshot.getValue<Long>()!!
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
        }

        // Load movies filter once (only guests)
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

        // Load session options once (only guests)
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

        // Load batches' UIDs list and keep it synced
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

        // Load active users' list and keep it synced
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

    /**
     * Exit the current session
     */
    fun leaveSession() {
        // Remove yourself from active users list
        if (SessionData.users != null) {
            sessionReference.child("users")
                .setValue(SessionData.users!!.filter { u -> u != SessionData.deviceId })
        }
    }

    /**
     * Return a reference to session's "movies" node
     */
    fun getMoviesReference() = sessionReference.child("movies")

    /**
     * Return a reference to session's "matches" node
     */
    fun getMatchesReference() = sessionReference.child("matches")
}