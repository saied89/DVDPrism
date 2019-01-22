package android.saied.com.filmcompass.ui.favoriteList

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

open class FavoritesViewModel(movieRepository: MovieRepository) : ViewModel() {
    open val favoritesLiveData: LiveData<List<Movie>> = movieRepository.getFavMovies()
}