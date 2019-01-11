package android.saied.com.backend.task

import android.saied.com.backend.MovieRepository
import android.saied.com.backend.checkMoviesOmdbDetails
import android.saied.com.backend.fetcher.OmdbFetcher
import android.saied.com.backend.getDvdYear
import android.saied.com.common.model.Movie
import android.saied.com.common.model.OmdbDetails
import android.saied.com.moviefetcher.MovieFetcher
import arrow.core.Success
import arrow.core.Try
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MovieFetcherTask(
    private val repository: MovieRepository,
    private val movieFetcher: MovieFetcher,
    private val omdbFetcher: OmdbFetcher
) {

    private val fetchJob = Job()
    private val serviceScope = CoroutineScope(fetchJob)

    var timer: Timer? = null

    private suspend fun fetchMovies() {
        logFetchTaskStart()
        val moviesResult = movieFetcher.fetchMovies()
        when (moviesResult) {
            is Try.Failure -> {
                throw moviesResult.exception
            }
            is Try.Success -> {
                val moviesWithDetails = moviesResult.value.map { movie ->
                    movie.copy(omdbDetails = fetchMoviesOmdbData(movie))
                }
                repository.saveMovies(moviesWithDetails)
                logFetchTaskSuccess()
            }
        }
    }

    private suspend fun fetchMoviesOmdbData(movie: Movie): OmdbDetails? {
        if (disambiguationMap.containsKey(movie.name)) {
            val omdbDetailsTry = omdbFetcher.getOmdbDetailsById(imdbId = disambiguationMap[movie.name]!!)
            return if (omdbDetailsTry is Try.Success)
                omdbDetailsTry.value
            else
                null
        }
        var omdbDetailsTry = omdbFetcher.getOmdbDetailsByTitle(movie.name)
        if (omdbDetailsTry is Try.Success && movie.checkMoviesOmdbDetails(omdbDetailsTry.value)) {
            return omdbDetailsTry.value
        } else {
            (movie.getDvdYear()..movie.getDvdYear() - 2).forEach {
                omdbDetailsTry = omdbFetcher.getOmdbDetailsByTitle(movie.name, it)
                if (omdbDetailsTry is Success && movie.checkMoviesOmdbDetails((omdbDetailsTry as Try.Success).value)
                ) {
                    return (omdbDetailsTry as Try.Success).value
                }
            }
            return null
        }
    }

    fun initRepeatingTask(period: Long = 120 * 60 * 1000) { //every two hours
        timer?.cancel() //in case another task is already running2
        timer = fixedRateTimer("movieFetcher", initialDelay = 0, period = period) {
            serviceScope.launch {
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

    val disambiguationMap = mapOf(
        "Trouble" to "tt5689632",
        "A.X.L." to "tt5709188",
        "The Oath" to "tt7461200",
        "Daughters of the Sexual Revolution: The Untold Story of the Dallas Cowboys Cheerleaders" to "tt7980152"
    )
}
