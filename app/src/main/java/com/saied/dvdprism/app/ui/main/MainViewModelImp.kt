package com.saied.dvdprism.app.ui.main

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.common.model.ScoreIndication
import com.saied.dvdprism.app.repository.MovieRepository
import androidx.lifecycle.*
import androidx.paging.PagedList
import arrow.core.Try
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModelImp(movieRepository: MovieRepository) : MainViewModel(movieRepository) {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override val stateLiveData = MutableLiveData<MainState>()

    override val latestLiveData: LiveData<PagedList<Movie>> = Transformations.switchMap(stateLiveData) {
        movieRepository.getLatestMovies(
            minMetaScore = it.minMetaIndication.minScore!!,
            minUserScore = it.minUserIndication.minScore!!
        )
    }
    override val upcommingLiveData: LiveData<PagedList<Movie>> = Transformations.switchMap(stateLiveData) {
        movieRepository.getUpcomingMovies(
            minMetaScore = it.minMetaIndication.minScore!!,
            minUserScore = it.minUserIndication.minScore!!
        )
    }

    init {
        stateLiveData.value = MainState.Success(ScoreIndication.NEGATIVE, ScoreIndication.NEGATIVE)
    }

    override fun refreshMovies() {
        stateLiveData.value = stateLiveData.value?.toLoading()
        uiScope.launch {
            stateLiveData.value = movieRepository.refreshMovies().let {
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

sealed class MainState(val minMetaIndication: ScoreIndication, val minUserIndication: ScoreIndication) {

    class Loading(
        minMetaScore: ScoreIndication,
        minUserScore: ScoreIndication
    ) : MainState(minMetaScore, minUserScore)

    class Success(
        minMetaScore: ScoreIndication,
        minUserScore: ScoreIndication
    ) : MainState(minMetaScore, minUserScore)

    class Error(
        val throwable: Throwable,
        minMetaScore: ScoreIndication,
        minUserScore: ScoreIndication
    ) : MainState(minMetaScore, minUserScore)

    fun toError(throwable: Throwable): Error = MainState.Error(throwable, minMetaIndication, minUserIndication)
    fun toLoading(): Loading = MainState.Loading(minMetaIndication, minUserIndication)
    fun toSuccess(): Success = MainState.Success(minMetaIndication, minUserIndication)

    fun mutateMetaScore(score: ScoreIndication): MainState =
        when (this) {
            is Loading -> {
                Loading(score, minUserIndication)
            }
            is Success -> {
                Success(score, minUserIndication)
            }
            is Error -> {
                Error(throwable, score, minUserIndication)
            }
        }

}