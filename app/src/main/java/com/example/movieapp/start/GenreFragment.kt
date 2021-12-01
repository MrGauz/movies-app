package com.example.movieapp.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.movieapp.R
import com.example.movieapp.data.GenresData
import com.example.movieapp.data.SessionData
import com.example.movieapp.databinding.FragmentGenreBinding
import com.example.movieapp.ui.GenresViewAdapter

class GenreFragment : Fragment() {
    private var _binding: FragmentGenreBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreBinding.inflate(inflater, container, false)

        val adapter = GenresViewAdapter(
            requireContext(),
            R.layout.genre_item_view,
            GenresData.getNamesList()
        )
        binding.genresGridView.adapter = adapter
        binding.genresGridView.setOnItemClickListener { parent, view, position, id ->
            val genreId = GenresData.findByName(
                ((view as ViewGroup).getChildAt(0) as TextView).text.toString()
            )?.id

            if (SessionData.filter.genres?.contains(genreId)!!) {
                // Genre already selected
                SessionData.filter.genres?.remove(genreId)
                view.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                view.setBackgroundResource(R.drawable.genre_item_background)
            } else {
                // Genre is not selected
                SessionData.filter.genres?.add(genreId!!)
                view.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                view.setBackgroundResource(R.drawable.genre_item_background_selected)
            }
        }

        binding.genreContinue.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_genreFragment_to_filterScreenFragment
            )
        )
        return binding.root
    }
}