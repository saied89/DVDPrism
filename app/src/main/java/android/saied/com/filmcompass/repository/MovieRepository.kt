package android.saied.com.filmcompass.repository

import android.saied.com.filmcompass.db.MovieDAO
import android.saied.com.filmcompass.network.MovieFetcher
import arrow.core.Try
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MovieRepository(
    private val movieFetcher: MovieFetcher,
    private val movieDAO: MovieDAO
) {

    suspend fun refreshMovies(): Try<Unit> =
        withContext(Dispatchers.IO) {
            val allMoviesTry = movieFetcher.fetchMovies()
            if (allMoviesTry is Try.Success) {
                try {
                    movieDAO.insertMovies(allMoviesTry.value)
                    Try.just(Unit)
                }catch (exp: Exception) {
                    Try.raise<Unit>(exp)
                }
            } else Try.raise((allMoviesTry as Try.Failure).exception)
        }

    fun getAllMovies() =
        movieDAO.getAllMovies()

}