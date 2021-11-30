package com.example.movieapp.data

import com.example.movieapp.models.Genre

object GenresData {
    var genres: List<Genre> = emptyList()

    fun findById(id: Long) = genres.find { g -> g.id == id }

    fun findByName(name: String) = genres.find { g -> g.name == name }

    fun getNamesList() = genres.map { g -> g.name }
}