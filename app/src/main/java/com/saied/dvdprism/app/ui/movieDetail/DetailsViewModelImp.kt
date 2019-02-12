package com.saied.dvdprism.app.ui.movieDetail

import com.saied.dvdprism.app.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModelImp(movieRepository: MovieRepository) : DetailsViewModel(movieRepository) {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun getIsFavoriteLiveData(title: String) = movieRepository.isMovieFavorite(title)

    override fun addToFavorites(title: String) {
        uiScope.launch { movieRepository.addToFavs(title) }
    }

    override fun removeFromFavorites(title: String) {
        uiScope.launch { movieRepository.removeFromFavs(title) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}