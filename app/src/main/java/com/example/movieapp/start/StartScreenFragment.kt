package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentStartScreenBinding

class StartScreenFragment : Fragment() {
    private var _binding: FragmentStartScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentStartScreenBinding.inflate(inflater, container, false)
        val startLobby = binding.startLobby
        startLobby.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_startScreenFragment_to_filterScreenFragment))
        val joinLobby = binding.joinLobby
        joinLobby.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_startScreenFragment_to_joinFragment))
        val root: View = binding.root
        return root
    }
}