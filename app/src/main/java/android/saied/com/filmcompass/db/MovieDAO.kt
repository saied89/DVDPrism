package android.saied.com.filmcompass.db

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.db.model.FavMovie
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDAO {
    @Query("SELECT * FROM Movie")
    fun getAllMovies(): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieList: List<Movie>)

    @Query("SELECT Movie.* FROM FavMovie INNER JOIN Movie ON Movie.id = movieId")
    fun getFavMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addToFav(favMovie: FavMovie)

    @Query("DELETE FROM FavMovie WHERE movieId = :movieId")
    fun deleteFav(movieId: Int)

    @Query("SELECT * FROM FavMovie WHERE movieId = :movieId")
    fun selectFav(movieId: Int): LiveData<FavMovie>

}