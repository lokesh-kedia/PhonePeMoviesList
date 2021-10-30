package com.phonepe.phonepemovies.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.phonepe.phonepemovies.data.local.AppDatabase
import com.phonepe.phonepemovies.data.local.MovieDao
import com.phonepe.phonepemovies.data.remote.MoviesRemoteDataSource
import com.phonepe.phonepemovies.data.remote.MoviesService
import com.phonepe.phonepemovies.data.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMovieService(retrofit: Retrofit): MoviesService = retrofit.create(MoviesService::class.java)

    
    @Provides
    fun provideMovieRemoteDataSource(moviesService: MoviesService) = MoviesRemoteDataSource(moviesService)

    
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    
    @Provides
    fun provideRepository(remoteDataSource: MoviesRemoteDataSource,
                          localDataSource: MovieDao) =
        MoviesRepository(remoteDataSource, localDataSource)
}