package android.saied.com.backend

import android.saied.com.common.model.Movie
import arrow.core.Try
import java.util.*

interface MovieRepository {

    fun initDB(movies: List<Movie>)

    fun saveMovies(movies: List<Movie>): Try<Unit>

    fun getMovies(): List<Movie>

//    fun getInfoLessMovies(): List<Movie>

//    fun setOmdbDataOfMovie(movie: Movie, omdbData: OmdbData): UpdateResult
}