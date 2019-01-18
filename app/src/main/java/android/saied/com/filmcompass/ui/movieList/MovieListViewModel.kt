package android.saied.com.filmcompass.ui.movieList

import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import arrow.core.Try

abstract class MovieListViewModel(protected val movieRepo: MovieRepository) : ViewModel() {
    abstract val stateLiveData: LiveData<MainState>
    abstract fun refreshMovies()
}