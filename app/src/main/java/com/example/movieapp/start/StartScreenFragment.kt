package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentStartScreenBinding

class StartScreenFragment : Fragment() {
    private var _binding: FragmentStartScreenBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_start_screen, container, false)
        _binding = FragmentStartScreenBinding.inflate(inflater, container, false)
        val startLobby = binding.startLobby
        startLobby.setOnClickListener {
            view.findNavController().navigate(R.id.action_startScreenFragment_to_filterScreenFragment)
        }
        val joinLobby = binding.joinLobby
        joinLobby.setOnClickListener {
            view.findNavController().navigate(R.id.action_startScreenFragment_to_joinFragment)
        }
        val root: View = binding.root
        return root
    }
}