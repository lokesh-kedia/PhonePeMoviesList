package com.phonepe.phonepemovies.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    val id: Long,
    val original_title: String,
    var overview: String,
    var popularity: Double,
    val poster_path: String,
    val release_date: String,
    val vote_average: Double
)