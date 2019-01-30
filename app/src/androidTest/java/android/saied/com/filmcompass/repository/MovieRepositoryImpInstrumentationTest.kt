package android.saied.com.filmcompass.repository

import android.content.Context
import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.db.MovieDAO
import android.saied.com.filmcompass.db.MovieDatabase
import android.saied.com.filmcompass.db.blockingObserve
import android.saied.com.filmcompass.network.MovieFetcher
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import arrow.core.Try
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieRepositoryImpInstrumentationTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var movieDao: MovieDAO
    private lateinit var db: MovieDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            MovieDatabase::class.java
        ).build()
        movieDao = db.movieDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun saveMovieAndVerifyLatest() {
        val dummyMovie = Movie("saied", 0, "", 0, 0, "")
//        val dummyMovie = Movie("saied", 0, "", 0, 0, "")
//        val dummyMovie = Movie("saied", 0, "", 0, 0, "")
        val mockFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.just(listOf(dummyMovie))
        }
        val subject: MovieRepository = MovieRepositoryImp(mockFetcher, movieDao)
        runBlocking { subject.refreshMovies() }

        val res = subject.getLatestMovies(1000).blockingObserve()

        TestCase.assertEquals(1, res?.size)
        TestCase.assertEquals(dummyMovie, res!![0])
    }

    @Test
    fun saveMovieAndVerifyUpcomming() {
        val dummyUpcoming = Movie("saied", 1200, "", 0, 0, "")
        val dummyLatest = Movie("saied", 900, "", 0, 0, "")
//        val dummyMovie = Movie("saied", 0, "", 0, 0, "")
//        val dummyMovie = Movie("saied", 0, "", 0, 0, "")
        val mockFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.just(listOf(dummyUpcoming))
        }
        val subject: MovieRepository = MovieRepositoryImp(mockFetcher, movieDao)
        runBlocking { subject.refreshMovies() }

        val res = subject.getUpcomingMovies(1000).blockingObserve()

        TestCase.assertEquals(1, res?.size)
        TestCase.assertEquals(dummyUpcoming, res!![0])
    }
}