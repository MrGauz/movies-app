package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.database.Database
import com.example.movieapp.database.models.*
import com.example.movieapp.databinding.FragmentFilterScreenBinding
import java.util.Collections.max
import java.util.Collections.min

class FilterScreenFragment : Fragment() {
    private var _binding: FragmentFilterScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentFilterScreenBinding.inflate(inflater, container, false)

        binding.genreButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_filterScreenFragment_to_genreFragment)
        )

        binding.createButton.setOnClickListener {
            // Compose data
            val releaseYearInterval = ReleaseYearInterval(
                min(binding.sliderRangeYears.values).toInt(),
                max(binding.sliderRangeYears.values).toInt()
            )
            val filter = Filter(
                genres = null, // TODO: take from GenreFragment
                releaseYear = releaseYearInterval,
                director = null,
                minRating = binding.sliderRangeRating.values[0].toDouble(),
                duration = DurationInterval(null, null) // TODO: get value from slider
            )
            val options = Options(
                matchPercentage = binding.sliderVotesPercentage.values[0].toDouble() / 100,
                joinTimer = 120 // TODO: get value from slider
            )

            // Create new session in database
            Database.createNewSession(filter, options)

            // Navigate to share screen
            FilterToSwipeItemList.updateswipeItemInfoArrayList(filter)
            binding.root.findNavController()
                .navigate(R.id.action_filterScreenFragment_to_shareFragment)
        }

        return binding.root
    }
}

