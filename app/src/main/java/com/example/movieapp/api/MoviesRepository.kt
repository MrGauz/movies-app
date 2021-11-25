package com.example.movieapp.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getGenres(page: Int = 1, language: String = "en-US") {
        api.getGenres(page = page, language = language)
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
        language: String = "en-US",
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
            language = language,
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
}