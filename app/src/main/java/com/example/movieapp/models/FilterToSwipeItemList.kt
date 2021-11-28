package com.example.movieapp.models

import java.util.ArrayList

object FilterToSwipeItemList {
    private var swipeItemInfoArrayList: ArrayList<Movie> = arrayListOf()
    var currentItemApiId: Long = 0
    fun updateswipeItemInfoArrayList(filter: Filter) { //<--- this doesnt work yet it pulls the data from the api
        swipeItemInfoArrayList.add(
            Movie(
                1,
                "test",
                "test",
                "test overview",
                "/2VgZW2ZD3pNoV2j8U2GXNRwOsk9.jpg",
                4f,
                "2021",
                listOf(1,2)
            )
        )
        swipeItemInfoArrayList.add(
            Movie(
                2,
                "test te2",
                "test te2",
                "test overview",
                "/zJNFiWBGYSMbfTAk6Ygqdj6Rahq.jpg",
                4f,
                "2021",
                listOf(1,2)
            )
        )
        if (swipeItemInfoArrayList.size > 0) { //<--- update currentID directly after the list is being created, so the first element is covered aswell
            currentItemApiId = swipeItemInfoArrayList[0].id
        }
    }

    fun getSwipeItemInfoArrayList(): ArrayList<Movie> {
        return swipeItemInfoArrayList.filter { m -> !m.isSwiped } as ArrayList<Movie>
    }

    fun setItemSwiped(
        movId: Long,
        direction: String
    ) {
        swipeItemInfoArrayList.find { m -> m.id == movId }?.isSwiped = true
    }
}