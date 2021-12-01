package com.example.movieapp.swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSwipeBinding
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.daprlabs.cardstack.SwipeDeck.SwipeEventCallback
import com.example.movieapp.data.SessionData
import com.example.movieapp.database.MatchesViewModelFactory
import com.example.movieapp.database.MoviesBatchViewModelFactory
import com.example.movieapp.models.AlertDialogBuilder
import com.example.movieapp.models.MatchesViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MoviesBatchViewModel
import java.util.ArrayList

class SwipeFragment : Fragment() {
    private lateinit var shownMovies: List<Movie>
    private lateinit var moviesBatchViewModel: MoviesBatchViewModel
    private lateinit var matchesViewModel: MatchesViewModel

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialogBuilder().createDialogOnBackButtonPress(
                    context,
                    activity,
                    R.style.AlertDialog
                )
            }
        })
    }

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
            if (matches.size >= 3) {
                binding.root.findNavController()
                    .navigate(R.id.action_swipeFragment_to_matchesFragment)
            }

            newMatch(matches)
        })

        binding.swipeDeck.setEventCallback(object : SwipeEventCallback {
            override fun cardSwipedLeft(position: Int) {
                moviesBatchViewModel.setSwiped(shownMovies[position])
            }

            override fun cardSwipedRight(position: Int) {
                // Test if a match
                // TODO: test if matches only from active users
                val acceptedCount =
                    shownMovies[position].acceptedBy.filter { u -> SessionData.users!!.contains(u) }.size + 1
                val minMatchCount =
                    SessionData.users!!.size * SessionData.options.matchPercentage / 100
                if (acceptedCount >= minMatchCount && SessionData.isJoinTimerOver()) {
                    moviesBatchViewModel.setMatch(shownMovies[position])
                }

                moviesBatchViewModel.setSwiped(shownMovies[position], true)
            }

            // This method is called when no card is present
            override fun cardsDepleted() {
                Toast.makeText(context, "Loading more movies...", Toast.LENGTH_SHORT).show()
            }

            // These methods are called when a card is moved up or down
            override fun cardActionDown() {}
            override fun cardActionUp() {}
        })

        // Button to navigate to matches screen
        binding.matchesButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_swipeFragment_to_matchesFragment)
        )
        return binding.root
    }

    private fun newMatch(matches: MutableList<Movie>) {
        binding.matchesButton.text = matches.size.toString()
        // TODO: show animation
    }
}