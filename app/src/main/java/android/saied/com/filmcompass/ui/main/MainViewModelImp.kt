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

    override val stateLiveData = MediatorLiveData<MainState>()

    init {
        stateLiveData.value = MainState.Success(null, null)
        stateLiveData.addSource(movieRepository.getLatestMovies()) {
            stateLiveData.value = stateLiveData.value?.mutateLatestMovieList(it)
        }
        stateLiveData.addSource(movieRepository.getUpcomingMovies()) {
            stateLiveData.value = stateLiveData.value?.mutateUpcomingMovieList(it)
        }
    }

    override fun refreshMovies() {
        stateLiveData.value = stateLiveData.value?.toLoading()
        uiScope.launch {
            stateLiveData.value = movieRepo.refreshMovies().let {
                if (it is Try.Failure) {
                    stateLiveData.value?.toError(it.exception)
                } else stateLiveData.value?.toSuccess()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

sealed class MainState(
    val latestMovieList: PagedList<Movie>?,
    val upcomingMovieList: PagedList<Movie>?
) {
    class Loading(latestMovies: PagedList<Movie>?, upcomingMovies: PagedList<Movie>?) :
        MainState(latestMovies, upcomingMovies)

    class Success(latestMovies: PagedList<Movie>?, upcomingMovies: PagedList<Movie>?) :
        MainState(latestMovies, upcomingMovies)

    class Error(val throwable: Throwable, latestMovies: PagedList<Movie>?, upcomingMovie: PagedList<Movie>?) :
        MainState(latestMovies, upcomingMovie)

    fun toError(throwable: Throwable): Error = MainState.Error(throwable, latestMovieList, upcomingMovieList)
    fun toLoading(): Loading = MainState.Loading(latestMovieList, upcomingMovieList)
    fun toSuccess(): Success = MainState.Success(latestMovieList, upcomingMovieList)

    fun mutateLatestMovieList(movieList: PagedList<Movie>): MainState =
        when (this) {
            is Loading -> Loading(
                movieList,
                upcomingMovieList
            )
            is Success -> Success(
                movieList,
                upcomingMovieList
            )
            is Error -> MainState.Error(
                throwable,
                movieList,
                upcomingMovieList
            )
        }

    fun mutateUpcomingMovieList(movieList: PagedList<Movie>): MainState =
        when (this) {
            is Loading -> Loading(
                latestMovieList,
                movieList
            )
            is Success -> Success(
                latestMovieList,
                movieList
            )
            is Error -> MainState.Error(
                throwable,
                latestMovieList,
                movieList
            )
        }
}