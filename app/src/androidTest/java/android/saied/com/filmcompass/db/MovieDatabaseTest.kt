package android.saied.com.filmcompass.db

import android.content.Context
import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.db.model.FavMovie
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.toLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
internal class MovieDatabaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var movieDAO: MovieDAO
    private lateinit var db: MovieDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MovieDatabase::class.java
        ).build()
        movieDAO = db.movieDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun saveMovieAndVerify() {
        val dummyMovie = Movie("saied", 0, "", 0, 0, "")

        movieDAO.insertMovies(listOf(dummyMovie))

        val res = movieDAO.getAllMovies().toLiveData(20).blockingObserve()
        assertEquals(1, res?.size)
        assertEquals(dummyMovie, res!![0])
    }

    @Test
    fun addMovieToFavsAndVerify() {
        val dummyMovies = listOf(
            Movie("saied", 0, "", 0, 0, ""),
            Movie("saied1", 0, "", 0, 0, ""),
            Movie("saied2", 0, "", 0, 0, "")
        )
        movieDAO.insertMovies(dummyMovies)
        val movies: List<Movie> = movieDAO.getAllMovies().toLiveData(20).blockingObserve()!!
        movieDAO.addToFav(FavMovie(movies[0].name))
        movieDAO.addToFav(FavMovie(movies[2].name))

        val res = movieDAO.getFavMovies()
        assertEquals(2, res.size)
        assertEquals(res[0], movies[0])
        assertEquals(res[1], movies[2])
    }

    @Test
    fun deleteFavAndVerify() {
        movieDAO.insertMovies(
            listOf(
                Movie("saied", 0, "", 0, 0, ""),
                Movie("saied1", 0, "", 0, 0, ""),
                Movie("saied2", 0, "", 0, 0, "")
            )
        )
        val movies: List<Movie> = movieDAO.getAllMovies().toLiveData(20).blockingObserve()!!
        movieDAO.addToFav(FavMovie(movies[0].name))
        movieDAO.addToFav(FavMovie(movies[2].name))

        movieDAO.deleteFav(movies[0].name)

        val res = movieDAO.getFavMovies()
        assertEquals(1, res.size)
        assertEquals(res[0], movies[2])
    }

    @Test
    fun selectFavoriteLiveDataIsUpdateOnDeleteAndAdd() {
        movieDAO.insertMovies(
            listOf(
                Movie("saied", 0, "", 0, 0, ""),
                Movie("saied1", 0, "", 0, 0, ""),
                Movie("saied2", 0, "", 0, 0, "")
            )
        )
        val movies: List<Movie> = movieDAO.getAllMovies().toLiveData(20).blockingObserve()!!

        val res = movieDAO.selectFav(movies[2].name)

        movieDAO.addToFav(FavMovie(movies[0].name))
        assertNull(res.blockingObserve())

        movieDAO.addToFav(FavMovie(movies[2].name))
        assertNotNull(res.blockingObserve() != null)
    }
}

private fun <T> LiveData<T>.blockingObserve(): T? {
    var value: T? = null
    val latch = CountDownLatch(1)

    val observer = Observer<T> { t ->
        value = t
        latch.countDown()
    }

    observeForever(observer)

    latch.await(2, TimeUnit.SECONDS)
    return value
}