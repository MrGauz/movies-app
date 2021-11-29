package com.example.movieapp.swipe

import android.os.Bundle
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
import com.example.movieapp.database.MoviesBatchViewModelFactory
import com.example.movieapp.models.FilterToSwipeItemList
import com.example.movieapp.models.Movie
import com.example.movieapp.models.MoviesBatchViewModel
import java.util.ArrayList


class SwipeFragment : Fragment() {
    private var cardStack: SwipeDeck? = null
    private var swipeItemInfoArrayList: ArrayList<Movie>? = null
    private lateinit var moviesBatchViewModel: MoviesBatchViewModel

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
        })

        cardStack?.setEventCallback(object : SwipeEventCallback {
            override fun cardSwipedLeft(position: Int) {
                updateCurrentItemApiID(position)

                val p = cardStack!![position]
                val title = moviesBatchViewModel.getBatch().value?.elementAt(position)?.title
                FilterToSwipeItemList.setItemSwiped(
                    swipeItemInfoArrayList!![position].id,
                    false
                )
                Toast.makeText(context, "Card Swiped Left on " + title, Toast.LENGTH_SHORT).show()
            }

            override fun cardSwipedRight(position: Int) {
                updateCurrentItemApiID(position)
                // on card swiped to right we are displaying a toast message.
                val title = swipeItemInfoArrayList!![position].title
                FilterToSwipeItemList.setItemSwiped(
                    swipeItemInfoArrayList!![position].id,
                    true
                )
                Toast.makeText(context, "Card Swiped Right " + title, Toast.LENGTH_SHORT).show()
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

    fun updateCurrentItemApiID(position: Int) {
        if (swipeItemInfoArrayList!!.size > position + 1) {//position +1 bec the current position was already swiped
            FilterToSwipeItemList.currentItemApiId =
                swipeItemInfoArrayList!![position + 1].id // write the ApiID of the next Item in our object class
        }
    }
}