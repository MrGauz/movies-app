package com.example.movieapp.models

data class Session(
    var id: String = "",
    var startTimestamp: Long = 0,
    var isActive: Boolean = false,
    var filter: Filter = Filter(),
    var options: Options = Options(),
) {
    var users: HashMap<String, String> = hashMapOf()
    var matches: HashMap<String, Movie> = hashMapOf()
    var movies: HashMap<String, List<Movie>> = hashMapOf()
}