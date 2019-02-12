package com.saied.dvdprism.backend.task

import com.saied.dvdprism.backend.MovieRepository
import com.saied.dvdprism.backend.checkMoviesOmdbDetails
import com.saied.dvdprism.backend.fetcher.MovieFetcher
import com.saied.dvdprism.backend.fetcher.OmdbFetcher
import com.saied.dvdprism.backend.fetcher.OmdbSearcher
import com.saied.dvdprism.backend.getDvdYear
import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.common.model.OmdbDetails
import arrow.core.Success
import arrow.core.Try
import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MovieFetcherTask(
    private val repository: MovieRepository,
    private val movieFetcher: MovieFetcher,
    private val omdbFetcher: OmdbFetcher,
    private val omdbSearcher: OmdbSearcher
) {

    private val fetchJob = Job()
    private val serviceScope = CoroutineScope(fetchJob)

    private var timer: Timer? = null

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
        val imdbId: String? = getMovieImdbId(movie)
        if (imdbId != null) {
            val omdbDetailsTry = omdbFetcher.getOmdbDetailsById(imdbId = imdbId)
            if (omdbDetailsTry is Try.Success)
                return omdbDetailsTry.value
        }
        var omdbDetailsTry = omdbFetcher.getOmdbDetailsByTitle(movie.name)
        if (omdbDetailsTry is Try.Success && movie.checkMoviesOmdbDetails(omdbDetailsTry.value)) {
            return omdbDetailsTry.value
        } else {
            (movie.getDvdYear() downTo movie.getDvdYear() - 2).forEach {
                omdbDetailsTry = omdbFetcher.getOmdbDetailsByTitle(movie.name, it)
                if (omdbDetailsTry is Success && movie.checkMoviesOmdbDetails((omdbDetailsTry as Try.Success).value)
                ) {
                    return (omdbDetailsTry as Try.Success).value
                }
            }
            return null
        }
    }

    private suspend fun getMovieImdbId(movie: Movie): String? {
        return if (disambiguationMap.containsKey(movie.name)) disambiguationMap[movie.name]!!
        else {
            val imdbIdTry = omdbSearcher.getImdbId(movie.name, movie.getDvdYear())
            if (imdbIdTry is Success) imdbIdTry.value else null
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

    private val disambiguationMap = mapOf(
        "Trouble" to "tt5689632",
        "A.X.L." to "tt5709188",
        "The Oath" to "tt7461200",
        "Daughters of the Sexual Revolution: The Untold Story of the Dallas Cowboys Cheerleaders" to "tt7980152"
    )
}
