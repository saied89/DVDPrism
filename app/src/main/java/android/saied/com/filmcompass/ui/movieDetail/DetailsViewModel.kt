package android.saied.com.filmcompass.ui.movieDetail

import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getIsFavoriteLiveData(title: String) = movieRepository.isMovieFavorite(title)

    fun addToFavorites(title: String) = uiScope.launch { movieRepository.addToFavs(title) }

    fun removeFromFavorites(title: String) = uiScope.launch { movieRepository.removeFromFavs(title) }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}