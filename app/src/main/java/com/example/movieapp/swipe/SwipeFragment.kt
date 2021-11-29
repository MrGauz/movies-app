package com.example.movieapp.swipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.daprlabs.cardstack.SwipeDeck
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSwipeBinding
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.daprlabs.cardstack.SwipeDeck.SwipeEventCallback
import com.example.movieapp.database.MatchesViewModelFactory
import com.example.movieapp.database.MoviesBatchViewModelFactory
import com.example.movieapp.models.FilterToSwipeItemList
import com.example.movieapp.models.MatchesViewModel
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MoviesBatchViewModel
import java.util.ArrayList


class SwipeFragment : Fragment() {
    private var cardStack: SwipeDeck? = null
    private lateinit var movies: List<Movie>
    private lateinit var moviesBatchViewModel: MoviesBatchViewModel
    private lateinit var matchesViewModel: MatchesViewModel

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe, container, false)
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)

        val factory = MoviesBatchViewModelFactory()
        moviesBatchViewModel =
            ViewModelProviders.of(this, factory).get(MoviesBatchViewModel::class.java)

        cardStack = view.findViewById(R.id.swipe_deck)

        moviesBatchViewModel.getBatch().observe(viewLifecycleOwner, { movies ->
            cardStack?.setAdapter(DeckAdapter(movies as ArrayList<Movie>, requireContext()))
            this.movies = movies
        })

        // Show realtime matches count
        val matchesFactory = MatchesViewModelFactory()
        matchesViewModel =
            ViewModelProviders.of(this, matchesFactory).get(MatchesViewModel::class.java)
        matchesViewModel.getMatches().observe(viewLifecycleOwner, { matches ->
            view.findViewById<Button>(R.id.matchesButton).text = matches.size.toString()
        })

        cardStack?.setEventCallback(object :
            SwipeEventCallback { //<----we gotta get the position of the Item in the List somehow, then it is possible to get the id
            override fun cardSwipedLeft(position: Int) {
                movies[position].isSwiped = true
                Toast.makeText(context, "Swiped left", Toast.LENGTH_SHORT).show()
            }

            override fun cardSwipedRight(position: Int) {
                Toast.makeText(context, "Swiped right", Toast.LENGTH_SHORT).show()
            }

            // This method is called when no card is present
            override fun cardsDepleted() {
                Toast.makeText(context, "No more courses present", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cardActionDown() {}
            override fun cardActionUp() {}
        })

        view.findViewById<Button>(R.id.matchesButton).setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_swipeFragment_to_matchesFragment)
        )

        return view
    }
}