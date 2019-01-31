package android.saied.com.filmcompass.ui.movieList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
//class MovieListViewModelAndroidTest {
//    @get:Rule
//    var rule: TestRule = InstantTaskExecutorRule()

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

//    @Test
//    fun repositoryIsCalledOnRefreshMovies() {
//        val mockRepository = mockk<MovieRepository> {
//            val liveData = MutableLiveData<PagedList<Movie>>()
//            every { getAllMovies() } returns liveData
//            coEvery { refreshMovies() } returns Try.just(Unit)
//        }
//        val subject = MainViewModelImp(mockRepository)
//        subject.refreshMovies()
//
//        coVerify(exactly = 1) {
//            mockRepository.refreshMovies()
//        }
//    }
//}