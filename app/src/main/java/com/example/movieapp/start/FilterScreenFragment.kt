package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.data.SessionData
import com.example.movieapp.database.Database
import com.example.movieapp.databinding.FragmentFilterScreenBinding
import com.example.movieapp.models.*
import java.text.NumberFormat
import java.util.*
import java.util.Collections.max
import java.util.Collections.min

class FilterScreenFragment : Fragment() {
    private var _binding: FragmentFilterScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var country: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        _binding = FragmentFilterScreenBinding.inflate(inflater, container, false)

        SessionData.isHost = true

        // Initialize UI values
        applyConfigValues()

        binding.genreButton.setOnClickListener{
            updateConfigValues()
            binding.root.findNavController().navigate(R.id.action_filterScreenFragment_to_genreFragment)
        }

        val spinner = binding.countrySpinner
        spinner.onItemSelectedListener
        val genres: List<String> =
            listOf("Country...", "Germany", "Georgia", "France") //Elements of the dropdown
        val spinnerAdapter =
            ArrayAdapter<String>(this.requireContext(), R.layout.custom_spinner_item, genres)
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                country = "none"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) { //get the selected item
                if (parent != null) {
                    country = parent.getItemAtPosition(position).toString()
                }
            }

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

    private fun updateConfigValues() {
        val releaseYearInterval = ReleaseYearInterval(
            min(binding.sliderRangeYears.values).toInt(),
            max(binding.sliderRangeYears.values).toInt()
        )
        val durationInterval = DurationInterval(
            min(binding.sliderRangeDuration.values).toInt(),
            max(binding.sliderRangeDuration.values).toInt()
        )

        SessionData.filter.releaseYear = releaseYearInterval
        SessionData.filter.minRating = binding.sliderRangeRating.values[0].toDouble()
        SessionData.filter.duration = durationInterval

        SessionData.options.matchPercentage = binding.sliderVotesPercentage.values[0].toInt()
        SessionData.options.joinTimer = binding.joinTimerSlider.values[0].toInt()
    }

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

        // Options
        binding.sliderVotesPercentage.setValues(SessionData.options.matchPercentage.toFloat())
        binding.joinTimerSlider.setValues(SessionData.options.joinTimer.toFloat())
    }
}