package com.saied.dvdprism.app.ui.favoriteList

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.RecyclerViewMatchers
import com.saied.dvdprism.app.ui.main.MainActivity
import com.saied.dvdprism.app.ui.movieDetail.DetailActivity
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.declare
import org.koin.test.mock.declareModule

@RunWith(AndroidJUnit4::class)
class FavoritesActivityTest : KoinTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testMovieListRender() {
        val element = Movie("", 0, "", 0, 0, "")
        declareModule {
                viewModel {
                    mockk<FavoritesViewModel>(relaxUnitFun = true) {
                        every { favoritesLiveData } returns MutableLiveData<List<Movie>>().apply {
                            value = listOf(element, element, element, element)
                        }
                    }
                }

        }

        ActivityScenario.launch(FavoritesActivity::class.java)
        onView(withId(R.id.recyclerView)).check(matches(RecyclerViewMatchers.hasItemCount(4)))
        onView(withId(R.id.noFavoriteTV)).check(matches(not(isDisplayed())))
    }

    @Test
    fun emptyListShowsNoFavoriteTV() {
        declareModule {
                viewModel(override = true) {
                    mockk<FavoritesViewModel>(relaxUnitFun = true) {
                        every { favoritesLiveData } returns MutableLiveData<List<Movie>>().apply {
                            value = listOf()
                        }
                    }
                }
        }

        ActivityScenario.launch(FavoritesActivity::class.java)

        onView(withId(R.id.recyclerView)).check(matches(RecyclerViewMatchers.hasItemCount(0)))
        onView(withId(R.id.noFavoriteTV)).check(matches(isDisplayed()))
    }

    @Test
    fun clickOnItemOpensDetailsActivity() {
        val element = Movie("", 0, "", 0, 0, "")
        declareModule {
                viewModel {
                    mockk<FavoritesViewModel>(relaxUnitFun = true) {
                        every { favoritesLiveData } returns MutableLiveData(listOf(element, element, element, element))
                    }
            }
        }
        ActivityScenario.launch(FavoritesActivity::class.java)

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FavoriteViewHolder>(
                0,
                click()
            )
        )

        intended(hasComponent(DetailActivity::class.qualifiedName))
    }


}