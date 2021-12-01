package com.example.movieapp.swipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.models.MovieDetails
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.models.MatchesViewModel
import com.example.movieapp.models.PosterSize

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
        if (arguments?.getLong("apiId") != null) {
            MoviesRepository.getMovieDetails(
                api_id = arguments?.getLong("apiId")!!,
                fragment = this
            )

        }

        return binding.root
    }

    fun showLoadedMovieDetails(movieDetails: MovieDetails) {
        val posterView = binding.detailsImage
        Glide.with(posterView).load(movieDetails.getPosterUrl(PosterSize.MIDDLE))
            .into(posterView)
        binding.titleText.text = movieDetails.title
        binding.detailsGenresText.text =
            movieDetails.genres.joinToString(separator = " | ") { g -> g.name }
        binding.detailsDirectorText.text = "directed by ${movieDetails.getDirectorName()}"
        binding.descriptionText.text = movieDetails.overview
        binding.tmdbRatingText.text = "${movieDetails.rating} / 10"

        binding.trailerButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(movieDetails.getTrailerUrl())))
        }

        binding.imdbButton.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(movieDetails.getImdbLink())))
        }
    }
}