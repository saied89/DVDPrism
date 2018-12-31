package android.saied.com.backend.services

import android.saied.com.backend.MovieRepository
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
        val movieFetcher = mockk<MovieFetcher>()
        coEvery { movieFetcher.fetchMovies() } returns Try.just(listOf())
        val subject = MovieFetcherTask(repository, movieFetcher)

        subject.initRepeatingTask(500)

        Thread.sleep(600)

        coVerify(exactly = 2) {
            movieFetcher.fetchMovies()
        }


    }
}