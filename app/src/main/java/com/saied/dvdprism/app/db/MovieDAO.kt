package com.saied.dvdprism.app.db

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.app.db.model.FavMovie
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface MovieDAO {
    @Query("SELECT * FROM Movie WHERE releaseDate <= :date AND (metaScore >= :minMetaScore OR metaScore IS NULL) And (userScore >= :minUserScore OR userScore IS NULL) ORDER BY releaseDate DESC")
    fun getLatestReleases(date: Long, minMetaScore: Int, minUserScore: Int): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM Movie WHERE releaseDate > :date AND (metaScore >= :minMetaScore OR metaScore IS NULL) And (userScore >= :minUserScore OR userScore IS NULL) ORDER BY releaseDate ASC")
    fun getUpcomingReleases(date: Long, minMetaScore: Int, minUserScore: Int): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movieList: List<Movie>)

    @Query("SELECT Movie.* FROM FavMovie INNER JOIN Movie ON Movie.name = FavMovie.title")
    fun getFavMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addToFav(favMovie: FavMovie)

    @Query("DELETE FROM FavMovie WHERE title = :title")
    fun deleteFav(title: String)

    @Query("SELECT * FROM FavMovie WHERE title = :title")
    fun selectFav(title: String): LiveData<FavMovie>
}