package android.saied.com.filmcompass.ui.main.movieList

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.*
import arrow.core.Try
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieRepo: MovieRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val stateLiveData = MediatorLiveData<MainState>()

    init {
        stateLiveData.addSource(movieRepo.getAllMovies()) {
            stateLiveData.value = stateLiveData.value?.mutateMovieList(it)
        }
    }

    fun refreshMovies() {
        stateLiveData.value = MainState.Loading(stateLiveData.value?.movieList ?: emptyList())
        uiScope.launch {
            stateLiveData.value = movieRepo.refreshMovies().let {
                if (it is Try.Failure) {
                    MainState.Error(it.exception, stateLiveData.value!!.movieList)
                } else stateLiveData.value?.let {
                    MainState.Success(it.movieList)
                }
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

sealed class MainState(val movieList: List<Movie>) {
    class Loading(movies: List<Movie> = emptyList()) : MainState(movies)
    class Success(movies: List<Movie>) : MainState(movies)
    class Error(val throwable: Throwable, movies: List<Movie> = emptyList()) : MainState(movies)

    fun mutateMovieList(movieList: List<Movie>): MainState =
        when (this) {
            is Loading -> Loading(movieList)
            is Success -> Success(movieList)
            is Error -> Error(throwable, movieList)
        }
}