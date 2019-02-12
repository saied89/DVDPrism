package com.saied.dvdprism.app.ui.favoriteList

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.app.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

open class FavoritesViewModel(movieRepository: MovieRepository) : ViewModel() {
    open val favoritesLiveData: LiveData<List<Movie>> = movieRepository.getFavMovies()
}