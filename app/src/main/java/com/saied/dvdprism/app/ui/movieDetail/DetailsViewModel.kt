package com.saied.dvdprism.app.ui.movieDetail

import com.saied.dvdprism.app.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class DetailsViewModel(protected val movieRepository: MovieRepository) : ViewModel() {
    abstract fun getIsFavoriteLiveData(title: String): LiveData<Boolean>
    abstract fun addToFavorites(title: String)
    abstract fun removeFromFavorites(title: String)
}