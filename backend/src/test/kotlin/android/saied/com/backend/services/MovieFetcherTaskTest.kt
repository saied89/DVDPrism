package android.saied.com.backend.services

import android.saied.com.backend.MovieRepository
import android.saied.com.moviefetcher.MovieFetcher
import arrow.core.Try
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class MovieFetcherTaskTest {

    @Test
    fun `repeating task is called in intervals`() {
        val repository = mockk<MovieRepository>(relaxed = true)
        val movieFetcher = mockk<MovieFetcher>()
        every {
            runBlocking {
                movieFetcher.fetchMovies()
            }
        } returns Try.just(listOf())
        val subject = MovieFetcherTask(repository, movieFetcher)

        subject.initRepeatingTask(500)

        Thread.sleep(600)

        verify(exactly = 2) {
            runBlocking {
                movieFetcher.fetchMovies()
            }
        }

    }
}