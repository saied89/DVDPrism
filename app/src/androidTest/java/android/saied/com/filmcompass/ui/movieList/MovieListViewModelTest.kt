package android.saied.com.filmcompass.ui.movieList

import android.provider.Contacts
import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.repository.MovieRepository
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import arrow.core.Try
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class MovieListViewModelAndroidTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

//    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

//    @Before
//    fun setUp() {
//        Dispatchers.setMain(mainThreadSurrogate)
//    }
//
//    @AfterEach
//    fun tearDown() {
//        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
//        mainThreadSurrogate.close()
//    }

    @Test
    fun repositoryIsCalledOnRefreshMovies() {
        val mockRepository = mockk<MovieRepository> {
            val liveData = MutableLiveData<PagedList<Movie>>()
            every { getAllMovies() } returns liveData
            coEvery { refreshMovies() } returns Try.just(Unit)
        }
        val subject = MovieListViewModel(mockRepository)
        subject.refreshMovies()

        coVerify(exactly = 1) {
            mockRepository.refreshMovies()
        }
    }
}