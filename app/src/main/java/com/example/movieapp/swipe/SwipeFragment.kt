package com.example.movieapp.swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSwipeBinding
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.daprlabs.cardstack.SwipeDeck.SwipeEventCallback
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.data.SessionData
import com.example.movieapp.database.Database
import com.example.movieapp.ui.MatchesViewModelFactory
import com.example.movieapp.ui.MoviesBatchViewModelFactory
import com.example.movieapp.ui.AlertDialogBuilder
import com.example.movieapp.models.MatchesViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MoviesBatchViewModel
import com.example.movieapp.ui.DeckAdapter
import java.util.ArrayList

class SwipeFragment : Fragment() {
    private lateinit var shownMovies: List<Movie>
    private lateinit var moviesBatchViewModel: MoviesBatchViewModel
    private lateinit var matchesViewModel: MatchesViewModel

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)

        // Load movies batch
        val factory = MoviesBatchViewModelFactory()
        moviesBatchViewModel =
            ViewModelProviders.of(this, factory).get(MoviesBatchViewModel::class.java)
        moviesBatchViewModel.getBatch().observe(viewLifecycleOwner, { movies ->
            shownMovies = movies.filter { m -> !m.swipedBy.contains(SessionData.deviceId) }
            binding.swipeDeck.setAdapter(
                DeckAdapter(
                    this.shownMovies as ArrayList<Movie>,
                    requireContext()
                )
            )
        })

        // Show realtime matches count
        val matchesFactory = MatchesViewModelFactory()
        matchesViewModel =
            ViewModelProviders.of(this, matchesFactory).get(MatchesViewModel::class.java)
        matchesViewModel.getMatches().observe(viewLifecycleOwner, { matches ->
            if (matches.size >= 3 && Navigation.findNavController(//the second argument fixes a bug caused by the onBackButtonPressed override.
                    binding.root).currentDestination?.id == R.id.swipeFragment) {
                binding.root.findNavController()
                    .navigate(R.id.action_swipeFragment_to_matchesFragment)
            }

            newMatch(matches)
        })

        // Handle cards actions
        binding.swipeDeck.setEventCallback(object : SwipeEventCallback {
            override fun cardSwipedLeft(position: Int) {
                moviesBatchViewModel.setSwiped(shownMovies[position])
            }

            override fun cardSwipedRight(position: Int) {
                // Test if a match
                val acceptedCount =
                    shownMovies[position].acceptedBy.filter { u -> SessionData.users!!.contains(u) }.size + 1
                val minMatchCount =
                    SessionData.users!!.size * SessionData.options.matchPercentage / 100
                if (acceptedCount >= minMatchCount) {
                    matchesViewModel.addMatch(shownMovies[position])
                }

                moviesBatchViewModel.setSwiped(shownMovies[position], true)
            }

            // This method is called when no card is present
            override fun cardsDepleted() {
                SessionData.currentBatchIndex++
                if (SessionData.batchUids.size >= SessionData.currentBatchIndex + 1) {
                    // Batches are already in firebase -> trigger LiveData reload
                    Database.getMoviesReference()
                        .child(SessionData.batchUids[SessionData.currentBatchIndex - 1])
                        .child("uid")
                        .setValue(SessionData.batchUids[SessionData.currentBatchIndex - 1])
                } else {
                    // New batch should be loaded
                    MoviesRepository.getMovies(
                        SessionData.filter,
                        SessionData.currentBatchIndex + 1
                    )
                }
            }

            // These methods are called when a card is moved up or down
            override fun cardActionDown() {}
            override fun cardActionUp() {}
        })

        // Button to navigate to matches screen
        binding.matchesButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_swipeFragment_to_matchesFragment)
        )

        // Create alert on pressing back button
        AlertDialogBuilder().createDialogOnBackButtonPress(
            this.context,
            activity,
            R.style.AlertDialog,
            R.id.swipeFragment
        )
        return binding.root
    }

    /**
     * Defines behaviour if a new match is found
     */
    private fun newMatch(matches: MutableList<Movie>) {
        binding.matchesButton.text = matches.size.toString()
    }
}