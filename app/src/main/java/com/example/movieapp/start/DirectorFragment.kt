package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDirectorBinding
import com.example.movieapp.databinding.FragmentGenreBinding

class DirectorFragment : Fragment() {
    private var _binding: FragmentDirectorBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDirectorBinding.inflate(inflater, container, false)
        val continueButton = binding.directorContinue
        continueButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_directorFragment_to_filterScreenFragment))
        val root: View = binding.root
        return root
    }
}