package android.saied.com.filmcompass.db

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.db.model.FavMovie
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDAO {
    @Query("SELECT * FROM Movie")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieList: List<Movie>)

    @Query("SELECT Movie.* FROM FavMovie INNER JOIN Movie ON Movie.id = movieId")
    fun getFavMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addToFav(favMovie: FavMovie)
}