package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.data.LanguagesData
import com.example.movieapp.data.SessionData
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.FragmentFilterScreenBinding
import com.example.movieapp.models.*
import java.util.Collections.max
import java.util.Collections.min

class FilterFragment : Fragment() {
    private var _binding: FragmentFilterScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentFilterScreenBinding.inflate(inflater, container, false)

        SessionData.isHost = true

        // Prepare languages spinner
        binding.languageSpinner.adapter = ArrayAdapter(
            this.requireContext(),
            R.layout.custom_spinner_item,
            LanguagesData.getNamesList()
        )

        // Initialize UI values
        applyConfigValues()

        binding.genreButton.setOnClickListener {
            updateConfigValues()
            binding.root.findNavController()
                .navigate(R.id.action_filterScreenFragment_to_genreFragment)
        }

        binding.createButton.setOnClickListener {
            // Load values from UI
            updateConfigValues()

            // Create new session in database
            Database.createNewSession()

            // Initialize session data object
            Database.loadSessionData()

            // Load first batch of movies
            MoviesRepository.getMovies(SessionData.filter)

            // Navigate to share screen
            binding.root.findNavController()
                .navigate(R.id.action_filterScreenFragment_to_shareFragment)
        }

        return binding.root
    }

    /**
     * Saves UI values of filter and options to SessionData
     */
    private fun updateConfigValues() {
        // Filter
        val releaseYearInterval = Interval(
            min(binding.sliderRangeYears.values).toInt(),
            max(binding.sliderRangeYears.values).toInt()
        )
        val durationInterval = Interval(
            min(binding.sliderRangeDuration.values).toInt(),
            max(binding.sliderRangeDuration.values).toInt()
        )

        SessionData.filter.releaseYear = releaseYearInterval
        SessionData.filter.minRating = binding.sliderRangeRating.values[0].toDouble()
        SessionData.filter.duration = durationInterval
        SessionData.filter.language = LanguagesData.findByName(
            binding.languageSpinner.selectedItem as String
        )

        // Options
        SessionData.options.matchPercentage = binding.sliderVotesPercentage.values[0].toInt()
        SessionData.options.joinTimer = binding.joinTimerSlider.values[0].toInt()
    }

    /**
     * Loads SessionData filter and options values onto UI
     */
    private fun applyConfigValues() {
        // Filter
        binding.sliderRangeYears.setValues(
            SessionData.filter.releaseYear?.from?.toFloat(),
            SessionData.filter.releaseYear?.to?.toFloat()
        )
        binding.sliderRangeRating.setValues(SessionData.filter.minRating?.toFloat())
        binding.sliderRangeDuration.setValues(
            SessionData.filter.duration?.from?.toFloat(),
            SessionData.filter.duration?.to?.toFloat()
        )
        binding.languageSpinner.setSelection(
            LanguagesData.getNamesList().indexOf(SessionData.filter.language?.name)
        )

        // Options
        binding.sliderVotesPercentage.setValues(SessionData.options.matchPercentage.toFloat())
        binding.joinTimerSlider.setValues(SessionData.options.joinTimer.toFloat())
    }
}