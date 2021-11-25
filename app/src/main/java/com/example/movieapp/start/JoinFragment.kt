package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            // TODO: check session ID against the database
            val swipeIntent = Intents(inputSessionId, "user", this.context)
            swipeIntent.intentToSwipe()
        }
        return binding.root
    }
}