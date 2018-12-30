package android.saied.com.backend.services

import android.saied.com.backend.MovieRepository
import android.saied.com.moviefetcher.MovieFetcher
import arrow.core.Try
import arrow.core.getOrElse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import kotlin.concurrent.fixedRateTimer

class MovieFetcherTask(
    private val repository: MovieRepository,
    private val movieFetcher: MovieFetcher
) {

    private val fetchJob = Job()
    private val serviceScope = CoroutineScope(fetchJob)

    private suspend fun fetchMovies() {
        val moviesResult = movieFetcher.fetchMovies()
        when (moviesResult) {
            is Try.Failure -> {
                throw moviesResult.exception
            }
            is Try.Success -> {
                repository.saveMovies(moviesResult.value)
                logFetchTaskSuccess()
            }
        }
    }

    fun initRepeatingTask(period: Long = 120 * 60 * 1000) { //every two hours
        fixedRateTimer("movieFetcher", initialDelay = 0, period = period) {
            serviceScope.launch {
                logFetchTaskStart()
                fetchMovies()
            }

        }
    }

    private fun logFetchTaskStart() =
        with(LoggerFactory.getLogger(javaClass)) {
            info("fetch movies task started")
        }

    private fun logFetchTaskSuccess() =
        with(LoggerFactory.getLogger(javaClass)) {
            info("fetch movies task finished with success")
        }


}