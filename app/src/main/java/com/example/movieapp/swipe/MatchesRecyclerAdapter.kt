package com.example.movieapp.swipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.data.GenresData
import com.example.movieapp.models.Movie
import com.example.movieapp.models.PosterSize

class MatchesRecyclerAdapter(private val matches: List<Movie>, private val context: Context) :
    RecyclerView.Adapter<MatchesRecyclerAdapter.ViewHolder>() {

    // Defines UI element that are accessed during binding
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val poster: ImageView = view.findViewById(R.id.matchCardPoster)
        val titleAndYear: TextView = view.findViewById(R.id.matchCardTitleYear)
        val rating: TextView = view.findViewById(R.id.matchCardRating)
        val genres : TextView = view.findViewById(R.id.matchCardGenres)
    }

    // Create new views
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.match_card, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val match = matches[position]
        viewHolder.titleAndYear.text = "${match.title}, ${match.getReleaseYear()}"
        viewHolder.rating.text = "Rating: ${match.rating}/10"
        viewHolder.genres.text = GenresData.genres.filter { g -> match.genre_ids.contains(g.id) }.joinToString(separator = " | ") { g -> g.name }
        Glide.with(viewHolder.poster).load(match.getPosterUrl(PosterSize.THUMBNAIL))
            .into(viewHolder.poster)

        viewHolder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                R.id.action_matchesFragment_to_infoFragment,
                bundleOf("apiId" to match.id)
            )
        )
    }

    // Return the size of notes list (invoked by the layout manager)
    override fun getItemCount() = matches.size
}