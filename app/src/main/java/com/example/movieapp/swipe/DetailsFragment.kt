package com.example.movieapp.swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.api.models.MovieDetails
import com.example.movieapp.database.models.FilterToSwipeItemList
import com.example.movieapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        // Navigate back on button click
        binding.infoBack.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_infoFragment_to_swipeFragment)
        )

        // TODO: show "loading details" on the screen,
        //  in case internet connection is slow and details are not loaded instantly
        // Load movie details
        MoviesRepository.getMovieDetails(
            api_id = 100, //FilterToSwipeItemList.currentItemApiId, // TODO: pass API ID here
            fragment = this
        )

        return binding.root
    }

    fun showLoadedMovieDetails(movieDetails: MovieDetails) {
        binding.titleText.text = movieDetails.title
        binding.descriptionText.text = movieDetails.overview
        binding.tmdbRatingText.text = movieDetails.rating.toString()
    }
}