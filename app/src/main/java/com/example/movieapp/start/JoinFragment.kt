package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.FragmentJoinBinding

class JoinFragment : Fragment() {
    private var _binding: FragmentJoinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJoinBinding.inflate(inflater, container, false)

        binding.joinButton.setOnClickListener {
            val inputSessionId = binding.sessionIdInput.text.toString().trim()
            if (inputSessionId.isEmpty()) {
                binding.sessionIdInput.error = "Please provide a session ID"
                return@setOnClickListener
            }

            Database.joinSession(inputSessionId, this)
        }

        return binding.root
    }

    fun onSuccessfulSessionJoin() {
        // Navigate to swipe screen
        val swipeIntent = Intents(isHost = false, context = this.context)
        swipeIntent.intentToSwipe()
    }

    fun onFailedSessionJoin(error: String = "Could not connect to session :c") {
        binding.sessionIdInput.error = error
    }
}