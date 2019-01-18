package android.saied.com.filmcompass.repository

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.db.MovieDAO
import android.saied.com.filmcompass.db.model.FavMovie
import android.saied.com.filmcompass.network.MovieFetcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import arrow.core.Try
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val DATABASE_PAGE_SIZE = 10

class MovieRepositoryImp(movieFetcher: MovieFetcher, movieDAO: MovieDAO) : MovieRepository(movieFetcher, movieDAO) {

    override suspend fun refreshMovies(): Try<Unit> =
        withContext(Dispatchers.IO) {
            val allMoviesTry = movieFetcher.fetchMovies()
            if (allMoviesTry is Try.Success) {
                try {
                    movieDAO.insertMovies(allMoviesTry.value)
                    Try.just(Unit)
                } catch (exp: Exception) {
                    Try.raise<Unit>(exp)
                }
            } else Try.raise((allMoviesTry as Try.Failure).exception)
        }

    override fun getAllMovies(): LiveData<PagedList<Movie>> {
        return LivePagedListBuilder(
            movieDAO.getAllMovies(),
            DATABASE_PAGE_SIZE
        ).build()
    }

    override suspend fun addToFavs(title: String) =
        withContext(Dispatchers.IO) {
            movieDAO.addToFav(FavMovie(title))
        }

    override suspend fun removeFromFavs(title: String) =
        withContext(Dispatchers.IO) {
            movieDAO.deleteFav(title)
        }

    override fun isMovieFavorite(title: String): LiveData<Boolean> = Transformations.map(movieDAO.selectFav(title)) {
        it != null
    }


}