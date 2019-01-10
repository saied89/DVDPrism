package android.saied.com.backend.services

import android.saied.com.backend.MovieRepository
import android.saied.com.backend.dummyMovie
import android.saied.com.backend.dummyOmdbDetails
import android.saied.com.backend.fetcher.OmdbFetcher
import android.saied.com.backend.task.MovieFetcherTask
import android.saied.com.common.model.OmdbDetails
import android.saied.com.moviefetcher.MovieFetcher
import arrow.core.Try
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class MovieFetcherTaskTest {

    @Test
    fun `repeating task is called in intervals`() {
        val repository = mockk<MovieRepository>(relaxed = true)
        val movieFetcher = mockk<MovieFetcher> {
            val dummyMovies = listOf(dummyMovie, dummyMovie)
            coEvery { fetchMovies() } returns Try.just(dummyMovies)
        }
        val omdbFetcher = mockk<OmdbFetcher>(relaxed = true) {
            coEvery { getOmdbDetails(dummyMovie.name) } returns Try.just(dummyOmdbDetails)
        }
        val subject = MovieFetcherTask(repository, movieFetcher, omdbFetcher)

        subject.initRepeatingTask(500)
        Thread.sleep(600)

        coVerify(exactly = 2) {
            movieFetcher.fetchMovies()
        }
        coVerify(exactly = 4) {
            omdbFetcher.getOmdbDetails(dummyMovie.name)
        }

    }

    @Test
    fun `movies returned from movieFetcher and details from omdbFetcher are saved to repository`() {

    }
}