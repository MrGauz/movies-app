package com.example.movieapp.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.movieapp.api.models.*
import com.example.movieapp.database.models.Filter

object MoviesRepository {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getGenres(page: Int = 1) {
        api.getGenres(page = page)
            .enqueue(object : Callback<GenresListResponse> {
                override fun onResponse(
                    call: Call<GenresListResponse>,
                    response: Response<GenresListResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Genres: ${responseBody.genres}")
                            // TODO: save loaded genres locally
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<GenresListResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

    fun getMovies(filter: Filter, page: Int = 1) {
        getMovies(
            genre_ids = filter.genres ?: emptyList(),
            director_id = filter.director?.api_id,
            min_release_year = filter.releaseYear?.from,
            max_release_year = filter.releaseYear?.to,
            min_rating = filter.minRating,
            max_rating = null,
            min_duration = filter.duration?.from,
            max_duration = filter.duration?.to,
            page = page
        )
    }

    fun getMovies(
        genre_ids: List<Int> = emptyList(),
        director_id: Int? = null,
        min_release_year: Int? = null,
        max_release_year: Int? = null,
        min_rating: Double? = 4.0,
        max_rating: Double? = null,
        min_duration: Int? = 60,
        max_duration: Int? = 120,
        page: Int = 1,
        append_to_response: List<String> = listOf("videos", "credits")
    ) {
        api.getMovies(
            with_genres = genre_ids.joinToString(separator = ","),
            with_crew = director_id?.toString() ?: "",
            primary_release_date_gte = min_release_year?.toString() ?: "",
            primary_release_date_lte = max_release_year?.toString() ?: "",
            vote_average_gte = min_rating?.toString() ?: "",
            vote_average_lte = max_rating?.toString() ?: "",
            with_runtime_gte = min_duration?.toString() ?: "",
            with_runtime_lte = max_duration?.toString() ?: "",
            page = page,
            append_to_response = append_to_response.joinToString(separator = ",")
        )
            .enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            // TODO: filter by job=Director
                            Log.d("Repository", "Movies: ${responseBody.movies}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

    fun searchPerson(query: String, page: Int = 1) {
        api.searchPerson(page = page, query = query)
            .enqueue(object : Callback<PeopleListResponse> {
                override fun onResponse(
                    call: Call<PeopleListResponse>,
                    response: Response<PeopleListResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            Log.d("Repository", "Genres: ${responseBody.people}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<PeopleListResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }

    fun getMovieDetails(
        api_id: String,
        append_to_results: List<String> = listOf("videos")
    ) {
        api.getMovieDetails(
            api_id = api_id,
            append_to_response = append_to_results.joinToString(separator = ","),
        )
            .enqueue(object : Callback<MovieDetailsResponse> {
                override fun onResponse(
                    call: Call<MovieDetailsResponse>,
                    response: Response<MovieDetailsResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            val movieDetails = MovieDetails(responseBody)
                            println("Trailer link: ${movieDetails.getTrailerUrl()}")
                        } else {
                            Log.d("Repository", "Failed to get response")
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                    Log.e("Repository", "onFailure", t)
                }
            })
    }
}