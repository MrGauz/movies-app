package com.example.movieapp.swipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import java.util.*
import com.example.movieapp.R
import com.example.movieapp.models.Movie
import com.example.movieapp.models.PosterSize

class DeckAdapter(
    private val swipeInfoData: ArrayList<Movie>, private val context: Context
) : BaseAdapter() {
    override fun getCount(): Int {
        return swipeInfoData.size
    }

    override fun getItem(position: Int): Any {
        return swipeInfoData[position]
    }

    override fun getItemId(position: Int): Long {
        return swipeInfoData[position].id
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val cardView: View = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_swipe_item, parent, false)

        // Load movie info onto the card
        val movie = swipeInfoData[position]
        val posterView = cardView.findViewById<ImageView>(R.id.posterView)
        (cardView.findViewById<View>(R.id.titleId) as TextView).text = movie.title
        Glide.with(cardView).load(movie.getPosterUrl(PosterSize.BIG)).into(posterView)
        // TODO: show more info

        // Set OnClickListeners to open details screen
        val onClickListener = Navigation.createNavigateOnClickListener(
            R.id.action_swipeFragment_to_infoFragment,
            bundleOf("apiId" to movie.id)
        )
        cardView.setOnClickListener(onClickListener)
        posterView.setOnClickListener(onClickListener)


        return cardView
    }
}