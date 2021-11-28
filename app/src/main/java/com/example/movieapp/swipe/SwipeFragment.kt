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
import com.example.movieapp.models.FilterToSwipeItemList
import java.util.ArrayList


class SwipeFragment : Fragment() {
    private var cardStack: SwipeDeck? = null
    private var swipeItemInfoArrayList: ArrayList<SwipeItemInfo>? = null

    private var _binding: FragmentSwipeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_swipe, container, false)
        _binding = FragmentSwipeBinding.inflate(inflater, container, false)

        swipeItemInfoArrayList = FilterToSwipeItemList.getSwipeItemInfoArrayList()

        cardStack = view.findViewById(R.id.swipe_deck)

        val adapter = DeckAdapter(swipeItemInfoArrayList!!, view.context)
        cardStack?.setAdapter(adapter)

        cardStack?.setEventCallback(object : SwipeEventCallback { //<----we gotta get the position of the Item in the List somehow, then it is possible to get the id
            override fun cardSwipedLeft(position: Int) {
                updateCurrentItemApiID(position)//<----call this function for cardSwiped left aswell the function is implemented below
                // on card swipe left we are displaying a toast message.
                val title = swipeItemInfoArrayList!![position].filmTitle
                FilterToSwipeItemList.setItemSwiped(swipeItemInfoArrayList!![position].movID,"left")
                Toast.makeText(context, "Card Swiped Left on "+title, Toast.LENGTH_SHORT).show()
            }

            override fun cardSwipedRight(position: Int) {
                updateCurrentItemApiID(position)
                // on card swiped to right we are displaying a toast message.
                val title = swipeItemInfoArrayList!![position].filmTitle
                FilterToSwipeItemList.setItemSwiped(swipeItemInfoArrayList!![position].movID,"right")
                Toast.makeText(context, "Card Swiped Right"+title, Toast.LENGTH_SHORT).show()
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

    fun updateCurrentItemApiID(position : Int){//<--- implemented here
        if(swipeItemInfoArrayList!!.size>position+1) {//position +1 bec the current position was already swiped
            FilterToSwipeItemList.currentItemApiId =
                swipeItemInfoArrayList!![position + 1].movID // write the ApiID of the next Item in our object class
        }
    }
}