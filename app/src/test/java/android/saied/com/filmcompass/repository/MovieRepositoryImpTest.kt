package android.saied.com.filmcompass.repository

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.db.MovieDAO
import android.saied.com.filmcompass.network.MovieFetcher
import arrow.core.Failure
import arrow.core.Try
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class MovieRepositoryImpTest {

    @Test
    fun movieFetcherSuccessUpdatesDataBase() {
        val element = Movie("test title", 0, "", 0, 0, "")
        val captureSlot = CapturingSlot<List<Movie>>()
        val mockMovieFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.just(listOf(element, element, element))
        }
        val mockMovieDao = mockk<MovieDAO> {
            every { insertMovies(capture(captureSlot)) } returns Unit

        }
        val subject: MovieRepository = MovieRepositoryImp(mockMovieFetcher, mockMovieDao)

        runBlocking { subject.refreshMovies() }

        coVerify(exactly = 1) {
            mockMovieFetcher.fetchMovies()
        }
        coVerify(exactly = 1) {
            mockMovieDao.insertMovies(any())
        }
        assertEquals(3, captureSlot.captured.size)
        assertTrue(captureSlot.captured.all { it.name == element.name})
    }

    @Test
    fun movieFetcherFailureIsHandled() {
        val exp = Exception("exception message")
        val mockMovieFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.raise(exp)
        }
        val mockMovieDao = mockk<MovieDAO>()
        val subject: MovieRepository = MovieRepositoryImp(mockMovieFetcher, mockMovieDao)
        val res = runBlocking { subject.refreshMovies() }

        assertTrue(res.isFailure())
        assertEquals(exp, (res as Failure).exception)
    }

    @Test
    fun movieDaoFailureIsHandled() {
        val exp = Exception("exception message")
        val mockMovieFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.just(listOf())
        }
        val mockMovieDao = mockk<MovieDAO> {
            coEvery { insertMovies(any()) } throws exp
        }
        val subject: MovieRepository = MovieRepositoryImp(mockMovieFetcher, mockMovieDao)
        val res = runBlocking { subject.refreshMovies() }

        assertTrue(res.isFailure())
        assertEquals(exp, (res as Failure).exception)
    }
}