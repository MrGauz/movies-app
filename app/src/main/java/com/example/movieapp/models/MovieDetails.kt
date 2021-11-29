package com.example.movieapp.models

data class MovieDetails(val response: MovieDetailsResponse) {
    val id: Long = response.id
    val imdbId: String = response.imdb_id
    val title: String = response.title
    val originalTitle: String = response.original_title
    val overview: String = response.overview
    val posterPath: String = response.poster_path
    val rating: Float = response.rating
    val releaseDate: String = response.release_date
    val duration: Int = response.duration
    val genres: List<Genre> = response.genres
    val trailerKey: String? = response.videos.results.find { v ->
        v.site == "YouTube" && v.type == "Trailer"
    }?.key
    // TODO: director, screenplay by etc

    fun getTrailerUrl(): String? {
        if (trailerKey == null) return null

        return "https://www.youtube.com/watch?v=$trailerKey"
    }
    fun getPosterUrl(size: PosterSize) = "https://image.tmdb.org/t/p/${size.url_size}$posterPath"

}