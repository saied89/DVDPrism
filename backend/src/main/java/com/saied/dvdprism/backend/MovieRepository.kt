package com.saied.dvdprism.backend

import arrow.core.Try
import com.saied.dvdprism.common.model.Movie

interface MovieRepository {

    fun initDB(movies: List<Movie>)

    fun saveMovies(movies: List<Movie>): Try<Unit>

    fun getMovies(): List<Movie>

    fun getDetaillessMovies(): List<Movie>

//    fun getInfoLessMovies(): List<Movie>

//    fun setOmdbDataOfMovie(movie: Movie, omdbData: OmdbData): UpdateResult
}