package com.example.movieapp.swipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.database.models.FilterToSwipeItemList
import com.example.movieapp.databinding.FragmentDetailsBinding
import com.example.movieapp.databinding.FragmentGenreBinding
import com.example.movieapp.databinding.FragmentJoinBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val backButton = binding.infoBack
        backButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_infoFragment_to_swipeFragment))

        //hi Kirill, you asked me if it was possible to call the Api from here

        val movieDetails = MoviesRepository.getMovieDetails(FilterToSwipeItemList.currentItemApiId.toString())

        //im very confused by the parameter api_id of getMovieDetails being a string. I just assumed this was the id of the current movie (which is normally a double?). If that assumption wasnt correct -
        //- i m very sorry, please revert my changes in this case
        // i ve marked my implementations with <--- (The implementation works, i tested it)
        // for the parameters we need the api_id, we can pass this information to FilterToSwipeItemList from the SwipeFragment
        // also i dont know how to get the data from movieDetails, if you do, you can just pass it to the ui via binding.
        return binding.root
    }
}