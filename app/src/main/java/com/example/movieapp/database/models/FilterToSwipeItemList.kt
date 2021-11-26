package com.example.movieapp.database.models

import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.api.models.Movie
import com.example.movieapp.swipe.SwipeItemInfo
import java.util.ArrayList

object FilterToSwipeItemList {//this is wrong, the items have to be loaded from the database, not from the api
    private var swipeItemInfoArrayList: ArrayList<SwipeItemInfo> = arrayListOf()
    public var currentItemApiId : Long = 0
    fun updateswipeItemInfoArrayList(filter: Filter) { //<--- this doesnt work yet it pulls the data from the api
        /*val movies: List<Movie> = MoviesRepository.getMovies(filter)
        System.out.println("MoviesSiiiize  " + movies.size)
        for (movie in movies) {
            swipeItemInfoArrayList.add(
                SwipeItemInfo(
                    movie.title,
                    movie.genre_ids,
                    movie.poster_path,
                    null,
                    movie.id //<--- thats how the id is pulled from the api
                )
            )
        }

         */
        this.swipeItemInfoArrayList?.add(SwipeItemInfo("test", listOf(1,2),"https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg",null,4))
        this.swipeItemInfoArrayList?.add(SwipeItemInfo("test2", listOf(1,2),"https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg",null,7))
        if(swipeItemInfoArrayList.size>0){ //<--- update currentID directly after the list is being created, so the first element is covered aswell
            currentItemApiId = swipeItemInfoArrayList[0].movID
        }
    }

    fun getSwipeItemInfoArrayList(): ArrayList<SwipeItemInfo> {//only selects the items that havent been swiped yet
        val listOfUnswiped: ArrayList<SwipeItemInfo> = arrayListOf()
        for (item in swipeItemInfoArrayList){
            if (item.swiped == null){
                listOfUnswiped.add(item)
            }
        }
        return listOfUnswiped
    }

    fun setItemSwiped(movId:Long,direction:String) { //sets an item to swiped after it has been swiped in the DeckAdapter
        val newSwipeItemInfoArrayList : ArrayList<SwipeItemInfo> = swipeItemInfoArrayList
        for (item in newSwipeItemInfoArrayList){
            if (item.movID == movId){
                item.swiped = direction
            }
        }
        this.swipeItemInfoArrayList = newSwipeItemInfoArrayList
    }
}