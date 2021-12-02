package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentStartScreenBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStartScreenBinding.inflate(inflater, container, false)

        // Start new session button
        binding.startLobby.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startScreenFragment_to_filterScreenFragment)
        )

        // Join existing session button
        binding.joinLobby.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_startScreenFragment_to_joinFragment)
        )

        return binding.root
    }
}