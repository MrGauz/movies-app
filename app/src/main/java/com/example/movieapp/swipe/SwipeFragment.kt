package com.example.movieapp.swipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daprlabs.cardstack.SwipeDeck
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentSwipeBinding
import android.widget.Toast
import com.daprlabs.cardstack.SwipeDeck.SwipeEventCallback
import java.util.ArrayList


class SwipeFragment : Fragment() {
    private var cardStack: SwipeDeck? = null
    private var courseModalArrayList: ArrayList<CourseModal>? = null

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe, container, false)
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)
        courseModalArrayList = ArrayList()
        cardStack = view.findViewById(R.id.swipe_deck)

        courseModalArrayList!!.add(
            CourseModal(
                "C++",
                "30 days",
                "20 Tracks",
                "C++ Self Paced Course",
                R.drawable.gfg
            )
        )
        courseModalArrayList!!.add(
            CourseModal(
                "Java",
                "30 days",
                "20 Tracks",
                "Java Self Paced Course",
                R.drawable.gfg
            )
        )
        courseModalArrayList!!.add(
            CourseModal(
                "Python",
                "30 days",
                "20 Tracks",
                "Python Self Paced Course",
                R.drawable.gfg
            )
        )
        courseModalArrayList!!.add(
            CourseModal(
                "DSA",
                "30 days",
                "20 Tracks",
                "DSA Self Paced Course",
                R.drawable.gfg
            )
        )
        courseModalArrayList!!.add(
            CourseModal(
                "PHP",
                "30 days",
                "20 Tracks",
                "PHP Self Paced Course",
                R.drawable.gfg
            )
        )
        val adapter = DeckAdapter(courseModalArrayList!!, view.context)
        cardStack?.setAdapter(adapter)

        cardStack?.setEventCallback(object : SwipeEventCallback {
            override fun cardSwipedLeft(position: Int) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(context, "Card Swiped Left", Toast.LENGTH_SHORT).show()
            }

            override fun cardSwipedRight(position: Int) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(context, "Card Swiped Right", Toast.LENGTH_SHORT).show()
            }

            override fun cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(context, "No more courses present", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN")
            }

            override fun cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP")
            }
        })

        return view
    }
}