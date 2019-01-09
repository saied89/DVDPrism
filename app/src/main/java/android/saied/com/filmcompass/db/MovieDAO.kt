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

    @Query("SELECT * FROM FavMovie")
    fun getFavMovies(): List<FavMovie>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addToFav(favMovie: FavMovie)
}