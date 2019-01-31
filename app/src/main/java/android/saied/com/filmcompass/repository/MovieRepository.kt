package android.saied.com.filmcompass.repository

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.db.MovieDAO
import android.saied.com.filmcompass.network.MovieFetcher
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import arrow.core.Try
import java.util.*

abstract class MovieRepository(protected val movieFetcher: MovieFetcher, protected val movieDAO: MovieDAO) {
    abstract suspend fun refreshMovies(): Try<Unit>

    abstract fun getLatestMovies(now: Long = Date().time): LiveData<PagedList<Movie>>
    abstract fun getUpcomingMovies(now: Long = Date().time): LiveData<PagedList<Movie>>

    abstract suspend fun addToFavs(title: String)

    abstract suspend fun removeFromFavs(title: String)
    abstract fun isMovieFavorite(title: String): LiveData<Boolean>
    abstract fun getFavMovies(): LiveData<List<Movie>>
}