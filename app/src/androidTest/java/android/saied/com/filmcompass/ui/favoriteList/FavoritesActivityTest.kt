package android.saied.com.filmcompass.ui.favoriteList

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.R
import android.saied.com.filmcompass.RecyclerViewMatchers
import android.saied.com.filmcompass.ui.main.MainActivity
import android.saied.com.filmcompass.ui.movieDetail.DetailActivity
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.test.KoinTest
import org.koin.test.declare

@RunWith(AndroidJUnit4::class)
class FavoritesActivityTest : KoinTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testMovieListRender() {
        val element = Movie("", 0, "", 0, 0, "")
        declare {
            viewModel(override = true) {
                mockk<FavoritesViewModel>(relaxUnitFun = true) {
                    every { favoritesLiveData } returns MutableLiveData<List<Movie>>().apply {
                        value = listOf(element, element, element, element)
                    }
                }
            }
        }

        ActivityScenario.launch(FavoritesActivity::class.java)

        onView(withId(R.id.recyclerView)).check(matches(RecyclerViewMatchers.hasItemCount(4)))
    }

    @Test
    fun clickOnItemOpensDetailsActivity() {
        val element = Movie("", 0, "", 0, 0, "")
        declare {
            viewModel(override = true) {
                mockk<FavoritesViewModel>(relaxUnitFun = true) {
                    every { favoritesLiveData } returns MutableLiveData<List<Movie>>().apply {
                        value = listOf(element, element, element, element)
                    }
                }
            }
        }
        ActivityScenario.launch(FavoritesActivity::class.java)

        onView(withId(R.id.recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<FavoriteViewHolder>(
                1,
                click()
            )
        )

        intended(hasComponent(DetailActivity::class.qualifiedName))
    }


}