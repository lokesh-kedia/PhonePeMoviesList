package com.phonepe.phonepemovies.data.remote

import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesService: MoviesService
): BaseDataSource() {

    suspend fun getAllMovies() = getResult { moviesService.getAllMovies() }
}