package com.example.movieapp.database.models

import com.example.movieapp.api.MoviesRepository
import com.example.movieapp.api.models.Movie
import com.example.movieapp.swipe.SwipeItemInfo
import java.util.ArrayList

object FilterToSwipeItemList {
    private var swipeItemInfoArrayList: ArrayList<SwipeItemInfo> = arrayListOf()

    fun updateswipeItemInfoArrayList(filter: Filter) {
        val movies: List<Movie> = MoviesRepository.getMovies(filter)
        System.out.println("MoviesSiiiize  " + movies.size)
        for (movie in movies) {
            swipeItemInfoArrayList.add(
                SwipeItemInfo(
                    movie.title,
                    movie.genre_ids,
                    movie.poster_path
                )
            )
        }
        //this.swipeItemInfoArrayList?.add(SwipeItemInfo("test", listOf(1,2),"https://www.cleverfiles.com/howto/wp-content/uploads/2018/03/minion.jpg"))
    }

    fun getSwipeItemInfoArrayList(): ArrayList<SwipeItemInfo>? {
        return this.swipeItemInfoArrayList
    }
}