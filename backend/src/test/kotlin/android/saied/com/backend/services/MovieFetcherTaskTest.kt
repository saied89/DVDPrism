package android.saied.com.backend.services

import android.saied.com.backend.MovieRepository
import android.saied.com.backend.dummyMovie
import android.saied.com.backend.dummyOmdbDetails
import android.saied.com.backend.fetcher.OmdbFetcher
import android.saied.com.backend.fetcher.OmdbSearcher
import android.saied.com.backend.task.MovieFetcherTask
import android.saied.com.common.model.Movie
import android.saied.com.common.model.OmdbDetails
import android.saied.com.moviefetcher.MovieFetcher
import arrow.core.Try
import io.mockk.*
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockKExtension::class)
internal class MovieFetcherTaskTest {

    @Test
    fun `repeating task is called in intervals`() {
        val repository = mockk<MovieRepository>(relaxed = true)
        val movieFetcher = mockk<MovieFetcher> {
            val dummyMovies = listOf(dummyMovie, dummyMovie)
            coEvery { fetchMovies() } returns Try.just(dummyMovies)
        }
        val omdbFetcher = mockk<OmdbFetcher> {
            coEvery { getOmdbDetailsByTitle(dummyMovie.name) } returns Try.just(dummyOmdbDetails)
            coEvery { getOmdbDetailsById(any()) } returns Try.just(dummyOmdbDetails)
        }
        val omdbSearcher = mockk<OmdbSearcher> {
            coEvery { getImdbId(any(), any()) } returns Try.just("")
        }
        val subject = MovieFetcherTask(repository, movieFetcher, omdbFetcher, omdbSearcher)

        subject.initRepeatingTask(500)
        Thread.sleep(600)

        coVerify(exactly = 2) {
            movieFetcher.fetchMovies()
        }
        coVerify(exactly = 4) {
            omdbFetcher.getOmdbDetailsById(any())
        }

    }

    @Test
    fun `correct omdb details is set`() {
        val id = "tt1502407"
        val captureSlot = CapturingSlot<List<Movie>>()
        val mockRepo = mockk<MovieRepository>{
            every { saveMovies(movies = capture(captureSlot)) } returns Try.just(Unit)
        }
        val mockMovieFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.just(listOf(dummyMovie.copy(name = "holloween")))
        }
        val mockSearcher = mockk<OmdbSearcher> {
            coEvery { getImdbId("holloween", 1970) } returns Try.just(id)
        }
        val mockOmdbFetcher = mockk<OmdbFetcher> {
            coEvery { getOmdbDetailsById(id) } returns Try.just(dummyOmdbDetails.copy(title = "saiedMovie"))
        }

        val subject = MovieFetcherTask(mockRepo, mockMovieFetcher, mockOmdbFetcher, mockSearcher)

        subject.initRepeatingTask()

        subject.initRepeatingTask()
        Thread.sleep(1000)

        assertEquals(1, captureSlot.captured.size)
        assertNotNull(captureSlot.captured[0].omdbDetails)
        assertEquals("saiedMovie", captureSlot.captured[0].omdbDetails?.title)
    }

    @Test
    fun `movies returned from movieFetcher and details from omdbFetcher are saved to repository`() {

    }
}