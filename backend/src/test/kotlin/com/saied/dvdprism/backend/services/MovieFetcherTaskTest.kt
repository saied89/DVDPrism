package com.saied.dvdprism.backend.services

import arrow.core.Try
import com.saied.dvdprism.backend.MovieRepository
import com.saied.dvdprism.backend.dummyMovie
import com.saied.dvdprism.backend.dummyOmdbDetails
import com.saied.dvdprism.backend.fetcher.MovieFetcher
import com.saied.dvdprism.backend.fetcher.OmdbFetcher
import com.saied.dvdprism.backend.fetcher.OmdbSearcher
import com.saied.dvdprism.backend.task.MovieFetcherTask
import com.saied.dvdprism.common.model.Movie
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
        val mockRepo = mockk<MovieRepository> {
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
    fun `when omdbSearch fails getOmdbDetailsByTitle is tried`() {
        val movieTitle = "holloween"
        val captureSlot = CapturingSlot<List<Movie>>()
        val mockRepo = mockk<MovieRepository> {
            every { saveMovies(movies = capture(captureSlot)) } returns Try.just(Unit)
        }
        val mockMovieFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.just(listOf(dummyMovie.copy(name = movieTitle)))
        }
        val mockSearcher = mockk<OmdbSearcher> {
            coEvery { getImdbId(movieTitle, 1970) } returns Try.raise(Exception())
        }
        val mockOmdbFetcher = mockk<OmdbFetcher> {
            coEvery { getOmdbDetailsByTitle(movieTitle) } returns Try.just(dummyOmdbDetails.copy(title = movieTitle))
        }

        val subject = MovieFetcherTask(mockRepo, mockMovieFetcher, mockOmdbFetcher, mockSearcher)

        subject.initRepeatingTask()
        Thread.sleep(1000)

        coVerify(exactly = 1) { mockOmdbFetcher.getOmdbDetailsByTitle(movieTitle) }
        assertEquals(1, captureSlot.captured.size)
        assertNotNull(captureSlot.captured[0].omdbDetails)
        assertEquals(movieTitle, captureSlot.captured[0].omdbDetails?.title)
    }

    @Test
    fun `when omdbSearch and getOmdbDetailsByTitle fail setting year parameters is tried`() {
        val movieTitle = "holloween"
        val year = 1970
        val captureSlot = CapturingSlot<List<Movie>>()
        val mockRepo = mockk<MovieRepository> {
            every { saveMovies(movies = capture(captureSlot)) } returns Try.just(Unit)
        }
        val mockMovieFetcher = mockk<MovieFetcher> {
            coEvery { fetchMovies() } returns Try.just(listOf(dummyMovie.copy(name = movieTitle)))
        }
        val mockSearcher = mockk<OmdbSearcher> {
            coEvery { getImdbId(movieTitle, year) } returns Try.raise(Exception())
        }
        val mockOmdbFetcher = mockk<OmdbFetcher> {
            coEvery { getOmdbDetailsByTitle(movieTitle) } returns Try.just(dummyOmdbDetails.copy(title = "wrong title"))
            coEvery {
                getOmdbDetailsByTitle(
                    movieTitle,
                    year
                )
            } returns Try.just(dummyOmdbDetails.copy(title = "wrong title"))
            coEvery {
                getOmdbDetailsByTitle(
                    movieTitle,
                    year - 1
                )
            } returns Try.just(dummyOmdbDetails.copy(title = "wrong title"))
            coEvery {
                getOmdbDetailsByTitle(
                    movieTitle,
                    year - 2
                )
            } returns Try.just(dummyOmdbDetails.copy(title = movieTitle))
        }
        val subject = MovieFetcherTask(mockRepo, mockMovieFetcher, mockOmdbFetcher, mockSearcher)

        subject.initRepeatingTask()
        Thread.sleep(1000)

        coVerify(exactly = 1) { mockOmdbFetcher.getOmdbDetailsByTitle(movieTitle) }
        coVerify(exactly = 1) { mockOmdbFetcher.getOmdbDetailsByTitle(movieTitle, year - 1) }
        coVerify(exactly = 1) { mockOmdbFetcher.getOmdbDetailsByTitle(movieTitle, year - 2) }
        assertEquals(1, captureSlot.captured.size)
        assertNotNull(captureSlot.captured[0].omdbDetails)
        assertEquals(movieTitle, captureSlot.captured[0].omdbDetails?.title)
    }

    @Test
    fun `movies returned from movieFetcher and details from omdbFetcher are saved to repository`() {

    }
}