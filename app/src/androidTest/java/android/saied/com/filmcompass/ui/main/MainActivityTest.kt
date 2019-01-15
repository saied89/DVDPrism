package android.saied.com.filmcompass.ui.main

import android.content.Context
import android.saied.com.filmcompass.repository.MovieRepository
import android.saied.com.filmcompass.ui.movieList.MainState
import android.saied.com.filmcompass.ui.movieList.MovieListViewModel
import androidx.core.view.isVisible
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import arrow.core.Try
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.test.KoinTest
import org.koin.test.declare
import android.saied.com.filmcompass.R
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.annotation.IdRes
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiObject
import junit.framework.TestCase.assertTrue


@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @Test
    fun correctLoadingStateRender() {
        declare {
            viewModel(override = true) {
                val mockRepo = mockk<MovieRepository>() {
                    coEvery { refreshMovies() } returns Try.just(Unit)
                    every { getAllMovies() } returns MutableLiveData()
                }
                spyk(MovieListViewModel(mockRepo)) {
                    every { stateLiveData } returns MediatorLiveData<MainState>().apply {
                        value = MainState.Loading(null)
                    }
                }
            }
        }
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        assertTrue(uiObjectWithId(R.id.progressbar).exists())
    }

    @Test
    fun correctErrorStateRender() {
        val testMessage = "test message"
        declare {
            viewModel(override = true) {
                val mockRepo = mockk<MovieRepository>() {
                    coEvery { refreshMovies() } returns Try.just(Unit)
                    every { getAllMovies() } returns MutableLiveData()
                }
//                MovieListViewModel(mockRepo)
                spyk(MovieListViewModel(mockRepo)) {
                    every { stateLiveData } returns MediatorLiveData<MainState>().apply {
                        value = MainState.Error(Exception(testMessage), null)
                    }
                }
            }
        }
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(testMessage)))
    }

    fun uiObjectWithId(@IdRes id: Int): UiObject {
        val resourceId = InstrumentationRegistry.getInstrumentation().targetContext.resources.getResourceName(id)
        val selector = UiSelector().resourceId(resourceId)
        return UiDevice.getInstance(getInstrumentation()).findObject(selector)
    }
}