package android.saied.com.filmcompass.ui.main

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.ui.movieList.MainState
import androidx.lifecycle.MediatorLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.test.KoinTest
import org.koin.test.declare
import android.saied.com.filmcompass.R
import android.saied.com.filmcompass.RecyclerViewMatchers
import android.saied.com.filmcompass.asPagedList
import android.saied.com.filmcompass.ui.movieList.MovieListViewModel
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.annotation.IdRes
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiObject
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.rules.TestRule


@RunWith(AndroidJUnit4::class)
class MainActivityTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun correctSuccessStateRender() {
        val element = Movie("", 0, "", 0, 0, "")
        val mockData = listOf(element, element, element).asPagedList()
        declare {
            viewModel(override = true) {
                mockk<MovieListViewModel>(relaxUnitFun = true) {
                    every { stateLiveData } returns MediatorLiveData<MainState>().apply {
                        value = MainState.Success(mockData)
                    }
                }
            }
        }

        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.recyclerView)).check(matches(RecyclerViewMatchers.hasItemCount(mockData!!.size)))
    }

    @Test
    fun correctLoadingStateRender() {
        declare {
            viewModel(override = true) {
                mockk<MovieListViewModel>(relaxUnitFun = true) {
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
                mockk<MovieListViewModel>(relaxUnitFun = true) {
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