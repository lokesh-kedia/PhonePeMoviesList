package com.phonepe.phonepemovies.data.repository

import com.phonepe.phonepemovies.data.local.MovieDao
import com.phonepe.phonepemovies.data.remote.MoviesRemoteDataSource
import com.phonepe.phonepemovies.utils.performGetOperation
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MovieDao
) {


    fun getAllMovies() = performGetOperation(
        databaseQuery = { localDataSource.getAllMovies() },
        networkCall = { remoteDataSource.getAllMovies() },
        saveCallResult = { localDataSource.insertAll(it.results) }
    )
}