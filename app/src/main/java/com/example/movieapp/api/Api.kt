package com.example.movieapp.api

import com.example.movieapp.BuildConfig
import com.example.movieapp.models.GenresListResponse
import com.example.movieapp.models.MovieDetailsResponse
import com.example.movieapp.models.MoviesListResponse
import com.example.movieapp.models.PeopleListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): Call<GenresListResponse>

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
        @Query("region") region: String,
        @Query("with_genres") with_genres: String,
        @Query("primary_release_date.gte") primary_release_date_gte: String,
        @Query("primary_release_date.lte") primary_release_date_lte: String,
        @Query("vote_average.gte") vote_average_gte: String,
        @Query("vote_average.lte") vote_average_lte: String,
        @Query("with_runtime.gte") with_runtime_gte: String,
        @Query("with_runtime.lte") with_runtime_lte: String,
        @Query("append_to_response") append_to_response: String
    ): Call<MoviesListResponse>

    @GET("search/person")
    fun searchPerson(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US",
        @Query("query") query: String,
        @Query("sort_by") sort_by: String = "popularity.desc"
    ): Call<PeopleListResponse>

    @GET("movie/{api_id}")
    fun getMovieDetails(
        @Path("api_id") api_id: String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") append_to_response: String,
    ): Call<MovieDetailsResponse>
}