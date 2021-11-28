package com.example.movieapp.swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.FragmentJoinBinding
import com.example.movieapp.start.Intents

class JoinFragment : Fragment() {
    private var _binding: FragmentJoinBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJoinBinding.inflate(inflater, container, false)

        //If statement that checks if a sessionID already exists -> checks if its valid
        if(!Database.sessionId.isEmpty()){
            Database.joinSession(Database.sessionId, this)
        }
        else {
            binding.joinButton.setOnClickListener {
                val inputSessionId = binding.sessionIdInput.text.toString().trim()
                if (inputSessionId.isEmpty()) {
                    binding.sessionIdInput.error = "Please provide a session ID"
                    return@setOnClickListener
                }

                Database.joinSession(inputSessionId, this)
            }
        }

        return binding.root
    }

    fun onSuccessfulSessionJoin() {
        // Navigate to swipe screen
        //val swipeIntent = Intents(isHost = false, context = this.context)
        //swipeIntent.intentToSwipe()
        findNavController().navigate(R.id.action_joinFragment_to_swipeFragment)
    }

    fun onFailedSessionJoin(error: String = "Could not connect to session :c") {
        binding.sessionIdInput.error = error
    }
}