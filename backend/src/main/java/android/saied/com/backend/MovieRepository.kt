package android.saied.com.backend

import android.saied.com.common.model.Movie
import java.util.*

interface MovieRepository {

    fun initDB(movies: List<Movie>)

    fun saveMovies(movies: List<Movie>)

    fun getMovies(date: Date = Date(), page: Int = 0): List<Movie>

//    fun getInfoLessMovies(): List<Movie>

//    fun setOmdbDataOfMovie(movie: Movie, omdbData: OmdbData): UpdateResult
}