package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentGenreBinding
import com.example.movieapp.databinding.FragmentJoinBinding

class GenreFragment : Fragment() {
    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)
        val continueButton = binding.genreContinue
        continueButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_genreFragment_to_filterScreenFragment))
        val root: View = binding.root
        return root
    }
}