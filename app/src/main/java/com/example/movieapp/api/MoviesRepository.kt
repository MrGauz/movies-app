package com.example.movieapp.api

import android.util.Log
import com.example.movieapp.data.GenresData
import com.example.movieapp.database.Database
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.movieapp.models.*
import com.example.movieapp.swipe.DetailsFragment

object MoviesRepository {
    private val api: Api

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(Api::class.java)
    }

    fun getGenres() {
        api.getGenres()
            .enqueue(object : Callback<GenresListResponse> {
                override fun onResponse(
                    call: Call<GenresListResponse>,
                    response: Response<GenresListResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            GenresData.genres = responseBody.genres
                        }
                    }
                }

                override fun onFailure(call: Call<GenresListResponse>, t: Throwable) {}
            })
    }

    fun getMovies(filter: Filter, page: Int = 1) {
        getMovies(
            genre_ids = filter.genres ?: emptyList(),
            countryCode = filter.country?.code,
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
        genre_ids: List<Long> = emptyList(),
        countryCode: String?,
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
            region = countryCode ?: "",
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
                            Database.saveNewMoviesBatch(responseBody.movies)
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {}
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
                        }
                    }
                }

                override fun onFailure(call: Call<PeopleListResponse>, t: Throwable) {}
            })
    }

    fun getMovieDetails(
        api_id: Long,
        append_to_results: List<String> = listOf("videos"),
        fragment: DetailsFragment
    ) {
        api.getMovieDetails(
            api_id = api_id.toString(),
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
                            fragment.showLoadedMovieDetails(movieDetails)
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {}
            })
    }
}