package com.example.mymovieapp.dagger

import android.content.Context
import com.example.mymovieapp.data.GenreRepository
import com.example.mymovieapp.data.MoviesRepository
import com.example.mymovieapp.data.ReviewsRepository
import com.example.mymovieapp.data.remote.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideGenreRepository(@ApplicationContext context: Context, movieApi: MovieApi): GenreRepository = GenreRepository(context, movieApi)

    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(@ApplicationContext context: Context, movieApi: MovieApi): MoviesRepository = MoviesRepository(context, movieApi)

    @Provides
    @ViewModelScoped
    fun provideReviewsRepository(@ApplicationContext context: Context, movieApi: MovieApi): ReviewsRepository = ReviewsRepository(context, movieApi)
}