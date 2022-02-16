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

    /**
     * Loads a list of genres from the TMDB API
     * and saves loaded data to GenresData
     */
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

    /**
     * A public wrapper method that translates Filter
     * to getMovies() attributes
     *
     * @param filter Filter to query movies
     * @param page Page number of API response
     */
    fun getMovies(filter: Filter, page: Int = 1) {
        getMovies(
            genre_ids = filter.genres ?: emptyList(),
            language_code = filter.language?.code,
            min_release_year = filter.releaseYear?.from,
            max_release_year = filter.releaseYear?.to,
            min_rating = filter.minRating,
            max_rating = null,
            min_duration = filter.duration?.from,
            max_duration = filter.duration?.to,
            page = page
        )
    }

    /**
     * Loads movies from TMDB API by defined parameters
     *
     * @param genre_ids A list of genres' API IDs as defined in GenresData
     * @param language_code Original movie's language in ISO-638 format as defined in LanguagesData
     * @param min_release_year Minimal release year
     * @param max_release_year Maximal release year
     * @param min_rating Minimal TMDB rating
     * @param max_rating Maximal TMDB rating
     * @param min_duration Minimal movie duration in minutes
     * @param max_duration Maximal movie duration in minutes
     * @param page Page number of API response
     */
    private fun getMovies(
        genre_ids: List<Long> = emptyList(),
        language_code: String?,
        min_release_year: Int? = null,
        max_release_year: Int? = null,
        min_rating: Double? = 4.0,
        max_rating: Double? = null,
        min_duration: Int? = 60,
        max_duration: Int? = 120,
        page: Int = 1
    ) {
        api.getMovies(
            with_genres = genre_ids.joinToString(separator = ","),
            original_language = language_code ?: "",
            primary_release_date_gte = min_release_year?.toString() ?: "",
            primary_release_date_lte = max_release_year?.toString() ?: "",
            vote_average_gte = min_rating?.toString() ?: "",
            vote_average_lte = max_rating?.toString() ?: "",
            with_runtime_gte = min_duration?.toString() ?: "",
            with_runtime_lte = max_duration?.toString() ?: "",
            page = page
        )
            .enqueue(object : Callback<MoviesListResponse> {
                override fun onResponse(
                    call: Call<MoviesListResponse>,
                    response: Response<MoviesListResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            // Saves loaded movies batch to firebase
                            Database.saveNewMoviesBatch(responseBody.movies)
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {}
            })
    }

    /**
     * Searches for a person in TMDB API
     *
     * @param query Person's name or a part of it
     * @param page Page number of API response
     */
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

    /**
     * Loads all details about a movie,
     * including videos associated with it and it's crew and actors
     * Loaded data is then shown on the DetailsFragment
     *
     * @param api_id TMDB's API ID of the movie
     * @param append_to_results Extra values you want to load
     * @param fragment DetailsFragment instance, on which the data will be shown
     */
    fun getMovieDetails(
        api_id: Long,
        append_to_results: List<String> = listOf("videos", "credits"),
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
                            // Response in converted to MovieDetails and loaded onto the fragment
                            val movieDetails = MovieDetails(responseBody)
                            fragment.showLoadedMovieDetails(movieDetails)
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {}
            })
    }
}