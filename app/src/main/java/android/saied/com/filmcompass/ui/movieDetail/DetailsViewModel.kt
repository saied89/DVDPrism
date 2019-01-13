package android.saied.com.filmcompass.ui.movieDetail

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getIsFavoriteLiveData(movieId: Int) = movieRepository.isMovieFavorite(movieId)

    fun addToFavorites(movieId: Int) = uiScope.launch { movieRepository.addToFavs(movieId) }

    fun removeFromFavorites(movieId: Int) = uiScope.launch { movieRepository.removeFromFavs(movieId) }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}