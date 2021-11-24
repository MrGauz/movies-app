package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentFilterScreenBinding
import com.example.movieapp.databinding.FragmentStartScreenBinding

class FilterScreenFragment : Fragment() {
    private var _binding: FragmentFilterScreenBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterScreenBinding.inflate(inflater, container, false)
        val genreButton = binding.genreButton
        val directorButton = binding.directorButton
        val createButton = binding.createButton
        genreButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_filterScreenFragment_to_genreFragment))
        directorButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_filterScreenFragment_to_directorFragment))
        createButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_filterScreenFragment_to_shareFragment))
        val root: View = binding.root
        return root
    }

}