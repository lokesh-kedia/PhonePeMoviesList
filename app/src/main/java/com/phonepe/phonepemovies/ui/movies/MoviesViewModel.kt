package com.phonepe.phonepemovies.ui.movies

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.phonepe.phonepemovies.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel(), LifecycleObserver {

    val movies = repository.getAllMovies()
}
