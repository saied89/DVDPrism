package android.saied.com.filmcompass.ui.movieList

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import java.util.*

abstract class MovieListViewModel : ViewModel() {
    abstract val moviesLiveData: LiveData<PagedList<Movie>>
}

class LatestListViewModel(movieRepository: MovieRepository) : MovieListViewModel() {
    override val moviesLiveData: LiveData<PagedList<Movie>> = movieRepository.getLatestMovies(Date().time)
}

class UpcommingListViewModel(movieRepository: MovieRepository) : MovieListViewModel() {
    override val moviesLiveData: LiveData<PagedList<Movie>> = movieRepository.getUpcomingMovies(Date().time)
}