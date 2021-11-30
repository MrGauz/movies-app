package com.example.movieapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.example.movieapp.R
import com.example.movieapp.data.GenresData
import com.example.movieapp.data.SessionData
import com.example.movieapp.databinding.GenreItemViewBinding

internal class GenresViewAdapter internal constructor(
    context: Context,
    resource: Int,
    private val itemList: List<String>?
) : ArrayAdapter<GenresViewAdapter.GenreViewHolder>(context, resource) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private lateinit var itemBinding: GenreItemViewBinding

    override fun getCount(): Int {
        return itemList?.size ?: 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var convertView = view
        val holder: GenreViewHolder
        if (convertView == null) {
            itemBinding = GenreItemViewBinding.inflate(inflater)
            convertView = itemBinding.root
            holder = GenreViewHolder()
            holder.name = itemBinding.genreItemTitle
            convertView.tag = holder
        } else {
            holder = convertView.tag as GenreViewHolder
        }
        holder.name!!.text = itemList!![position]

        if (SessionData.filter.genres?.contains(GenresData.findByName(itemList[position])?.id)!!) {
            convertView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            convertView.setBackgroundResource(R.drawable.genre_item_background_selected)
        }

        return convertView
    }

    internal class GenreViewHolder {
        var name: TextView? = null
    }
}
