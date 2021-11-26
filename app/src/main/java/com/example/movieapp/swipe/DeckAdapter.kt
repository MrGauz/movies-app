package com.example.movieapp.swipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSwipeBinding

class DeckAdapter     // on below line we have created constructor for our variables.
    (// on below line we have created variables
    // for our array list and context.
    private val swipeInfoData: ArrayList<SwipeItemInfo>, private val context: Context
) : BaseAdapter() {
    override fun getCount(): Int {
        // in get count method we are returning the size of our array list.
        return swipeInfoData.size
    }

    override fun getItem(position: Int): Any {
        // in get item method we are returning the item from our array list.
        return swipeInfoData[position]
    }

    override fun getItemId(position: Int): Long {
        // in get item id we are returning the position.
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // in get view method we are inflating our layout on below line.
        var w : View? = convertView
        if (w == null) {
            // on below line we are inflating our layout.
            w = LayoutInflater.from(parent.context).inflate(R.layout.movie_swipe_item, parent, false)
        }
        val v : View= w!!
        // on below line we are initializing our variables and setting data to our variables.
        (v.findViewById<View>(R.id.titleId) as TextView).text =
            swipeInfoData[position].filmTitle
        (v.findViewById<View>(R.id.directorID) as TextView).text =
            swipeInfoData[position].genres
        (v.findViewById<View>(R.id.genresID) as TextView).text =
            swipeInfoData[position].director
        (v.findViewById<View>(R.id.posterView) as ImageView).setImageResource(
            swipeInfoData[position].imgId
        )
        return v
    }//source: https://www.geeksforgeeks.org/tinder-swipe-view-with-example-in-android/
}