package android.saied.com.filmcompass.ui.movieDetail

import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class DetailsViewModel(protected val movieRepository: MovieRepository) : ViewModel() {
    abstract fun getIsFavoriteLiveData(title: String): LiveData<Boolean>
    abstract fun addToFavorites(title: String)
    abstract fun removeFromFavorites(title: String)
}