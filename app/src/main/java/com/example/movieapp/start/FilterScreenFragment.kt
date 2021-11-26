package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.movieapp.R
import com.example.movieapp.database.models.Director
import com.example.movieapp.database.models.Filter
import com.example.movieapp.database.models.FilterToSwipeItemList
import com.example.movieapp.database.models.ReleaseYearInterval
import com.example.movieapp.databinding.FragmentFilterScreenBinding
import com.google.android.material.slider.RangeSlider
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
        val genreButton = binding.genreButton
//        val directorButton = binding.directorButton
        val createButton = binding.createButton
        genreButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_filterScreenFragment_to_genreFragment))
//        directorButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_filterScreenFragment_to_directorFragment))
        createButton.setOnClickListener(){
            //send gathered information to Filter Class
            val minYear = min(binding.sliderRangeYears.values)
            val maxYear = max(binding.sliderRangeYears.values)
            val releaseYearInterval = ReleaseYearInterval(minYear.toInt(),maxYear.toInt())
            val minRating : Double = binding.sliderRangeRating.values.get(0).toDouble()
            val votesPercentage : Int = binding.sliderVotesPercentage.values.get(0).toInt() // has to go to settings somehow
            val genre = null // TODO: 26.11.2021  implement genre fragment and get value
            val duration = null
            var director = null
            var filter = Filter(
                genre,
                releaseYearInterval,
                director,
                minRating,
                null
            )
            filter.duration = duration
            filter.release_year = releaseYearInterval
            filter.genres = genre
            filter.director = director
            filter.min_rating = minRating

            FilterToSwipeItemList.updateswipeItemInfoArrayList(filter)
            binding.root.findNavController().navigate(R.id.action_filterScreenFragment_to_shareFragment)
        }

        val root: View = binding.root
        return root
    }

    }

