package android.saied.com.backend.services

import android.saied.com.backend.MovieRepository
import android.saied.com.moviefetcher.MovieFetcher
import arrow.core.Try
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MovieFetcherTask(
    private val repository: MovieRepository,
    private val movieFetcher: MovieFetcher
) {

    private val fetchJob = Job()
    private val serviceScope = CoroutineScope(fetchJob)

    var timer: Timer? = null

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
        timer?.cancel() //in case another task is already running2
        timer = fixedRateTimer("movieFetcher", initialDelay = 0, period = period) {
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