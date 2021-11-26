package com.example.movieapp.swipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import com.example.movieapp.R

class DeckAdapter     // on below line we have created constructor for our variables.
    (// on below line we have created variables
    // for our array list and context.
    private val courseData: ArrayList<CourseModal>, private val context: Context
) : BaseAdapter() {
    override fun getCount(): Int {
        // in get count method we are returning the size of our array list.
        return courseData.size
    }

    override fun getItem(position: Int): Any {
        // in get item method we are returning the item from our array list.
        return courseData[position]
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
            w = LayoutInflater.from(parent.context).inflate(R.layout.course_rv_item, parent, false)
        }
        val v : View= w!!
        // on below line we are initializing our variables and setting data to our variables.
        (v.findViewById<View>(R.id.idTVCourseName) as TextView).text =
            courseData[position].courseName
        (v.findViewById<View>(R.id.idTVCourseDescription) as TextView).text =
            courseData[position].courseDescription
        (v.findViewById<View>(R.id.idTVCourseDuration) as TextView).text =
            courseData[position].courseDuration
        (v.findViewById<View>(R.id.idTVCourseTracks) as TextView).text =
            courseData[position].courseTracks
        (v.findViewById<View>(R.id.idIVCourse) as ImageView).setImageResource(
            courseData[position].imgId
        )
        return v
    }//source: https://www.geeksforgeeks.org/tinder-swipe-view-with-example-in-android/
}