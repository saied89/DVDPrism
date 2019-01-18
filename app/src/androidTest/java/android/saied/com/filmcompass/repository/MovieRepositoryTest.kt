package android.saied.com.filmcompass.repository

import android.content.Context
import android.saied.com.filmcompass.db.MovieDAO
import android.saied.com.filmcompass.db.MovieDatabase
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

class MovieRepositoryTest {
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

}