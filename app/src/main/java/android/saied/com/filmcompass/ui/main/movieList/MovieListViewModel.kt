package android.saied.com.filmcompass.ui.main.movieList

import android.saied.com.filmcompass.MovieRepository
import android.saied.com.filmcompass.model.Movie
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Try
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieRepo: MovieRepository) : ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val stateLiveData = MutableLiveData<MainState>()

    fun fetchMovies() {
        stateLiveData.value = MainState.Loading(stateLiveData.value?.movieList ?: emptyList())
        uiScope.launch {
            val res = movieRepo.fetchMovies().run {
                when(this) {
                    is Try.Success -> { MainState.Success(value) }
                    is Try.Failure -> { MainState.Error(this.exception, stateLiveData.value!!.movieList) }
                }
            }
            stateLiveData.value = res
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
}