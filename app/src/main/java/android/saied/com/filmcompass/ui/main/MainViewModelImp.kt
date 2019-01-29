package android.saied.com.filmcompass.ui.main

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.repository.MovieRepository
import androidx.lifecycle.*
import androidx.paging.PagedList
import arrow.core.Try
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MainViewModelImp(movieRepository: MovieRepository) : MainViewModel(movieRepository) {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _stateLiveData = MediatorLiveData<MainState>()
    override val stateLiveData: LiveData<MainState> = _stateLiveData

    init {
        _stateLiveData.addSource(movieRepo.getLatestMovies(Date().time)) {
            _stateLiveData.value = _stateLiveData.value?.mutateMovieList(it)
        }
    }

    override fun refreshMovies() {
        _stateLiveData.value = MainState.Loading(_stateLiveData.value?.movieList)
        uiScope.launch {
            _stateLiveData.value = movieRepo.refreshMovies().let {
                if (it is Try.Failure) {
                    MainState.Error(it.exception, _stateLiveData.value?.movieList)
                } else MainState.Success(_stateLiveData.value?.movieList)

            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

sealed class MainState(val movieList: PagedList<Movie>?) {
    class Loading(movies: PagedList<Movie>?) : MainState(movies)
    class Success(movies: PagedList<Movie>?) : MainState(movies)
    class Error(val throwable: Throwable, movies: PagedList<Movie>?) : MainState(movies)

    fun mutateMovieList(movieList: PagedList<Movie>): MainState =
        when (this) {
            is Loading -> Loading(
                movieList
            )
            is Success -> Success(
                movieList
            )
            is Error -> Error(
                throwable,
                movieList
            )
        }
}