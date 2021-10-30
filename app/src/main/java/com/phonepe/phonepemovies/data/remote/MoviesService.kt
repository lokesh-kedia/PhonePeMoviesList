package com.phonepe.phonepemovies.data.remote


import com.phonepe.phonepemovies.data.entities.MoviesList
import retrofit2.Response
import retrofit2.http.GET

interface MoviesService {
    @GET("/3/movie/now_playing?api_key=38a73d59546aa378980a88b645f487fc&language=en-US&page=1")
    suspend fun getAllMovies(): Response<MoviesList>

}