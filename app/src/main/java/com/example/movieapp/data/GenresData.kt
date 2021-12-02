package com.example.movieapp.data

import com.example.movieapp.models.Genre

/**
 * Contains a list of API's genres with their IDs
 */
object GenresData {
    var genres: List<Genre> = emptyList()

    /**
     * Searches by API ID
     *
     * @return Genre object
     */
    fun findById(id: Long) = genres.find { g -> g.id == id }

    /**
     * Searches by genre's name
     *
     * @return Genre object
     */
    fun findByName(name: String) = genres.find { g -> g.name == name }

    /**
     * Composes a list of all genres' names
     */
    fun getNamesList() = genres.map { g -> g.name }
}