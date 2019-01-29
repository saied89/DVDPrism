package android.saied.com.filmcompass.ui.movieList

import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.ViewModel
import java.util.*

class MovieListViewModel(movieRepository: MovieRepository) : ViewModel() {
    val moviesLiveData = movieRepository.getLatestMovies(Date().time)
}