package com.saied.dvdprism.app.repository

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.app.db.MovieDAO
import com.saied.dvdprism.app.network.MovieFetcher
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import arrow.core.Try
import java.util.*

abstract class MovieRepository(protected val movieFetcher: MovieFetcher, protected val movieDAO: MovieDAO) {
    abstract suspend fun refreshMovies(): Try<Unit>

    abstract fun getLatestMovies(
        now: Long = Date().time,
        minMetaScore: Int = 0,
        minUserScore: Int = 0
    ): LiveData<PagedList<Movie>>

    abstract fun getUpcomingMovies(
        now: Long = Date().time,
        minMetaScore: Int = 0,
        minUserScore: Int = 0
    ): LiveData<PagedList<Movie>>

    abstract suspend fun addToFavs(title: String)

    abstract suspend fun removeFromFavs(title: String)
    abstract fun isMovieFavorite(title: String): LiveData<Boolean>
    abstract fun getFavMovies(): LiveData<List<Movie>>
}