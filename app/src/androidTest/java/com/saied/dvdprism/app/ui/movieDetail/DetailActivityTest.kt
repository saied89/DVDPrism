package com.saied.dvdprism.app.ui.movieDetail

import android.app.Activity
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.RecyclerViewMatchers.drawableIsCorrect
import com.saied.dvdprism.app.ui.poster.PosterActivity
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.test.KoinTest
import org.koin.test.declare

@RunWith(AndroidJUnit4::class)
class DetailActivityTest : KoinTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(DetailActivity::class.java, false, false)

    @Test
    fun isFavoriteIsRenderedCorrectly() {
        val titleStr = "title"
        val element = Movie(titleStr, 0, "", 0, 0, "")
        val mockViewModel = mockk<DetailsViewModel>(relaxUnitFun = true) {
            every { getIsFavoriteLiveData(any()) } returns MutableLiveData<Boolean>().apply {
                postValue(true)
            }
        }
        declare {
            viewModel(override = true) {
                mockViewModel
            }
        }
        ActivityScenario.launch<DetailActivity>(
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                DetailActivity::class.java
            ).apply {
                putExtra(MOVIE_EXTRA_TAG, element)
            }
        )


        //test1: correct graphic is set
        onView(withId(R.id.favFab)).check(matches(drawableIsCorrect(R.drawable.ic_favorite_black_24dp)))

        //test2 correct behaviour
        onView(withId(R.id.favFab)).perform(click())
        verify(exactly = 0) { mockViewModel.addToFavorites(titleStr) }
        verify(exactly = 1) { mockViewModel.removeFromFavorites(titleStr) }
    }

    @Test
    fun clickOnBackArrowClosesActivity() {
        val element = Movie("", 0, "", 0, 0, "")
        val scenario = ActivityScenario.launch<DetailActivity>(
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                DetailActivity::class.java
            ).apply {
                putExtra(MOVIE_EXTRA_TAG, element)
            }
        )

        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())

        assertEquals(Activity.RESULT_CANCELED, scenario.result.resultCode)

//        scenario.onActivity {
//            assertTrue(it.isFinishing || it == null)
//        }

    }

    @Test
    fun notFavoriteIsRenderedCorrectly() {
        val titleStr = "title"
        val element = Movie(titleStr, 0, "", 0, 0, "")
        val mockViewModel = mockk<DetailsViewModel>(relaxUnitFun = true) {
            every { getIsFavoriteLiveData(any()) } returns MutableLiveData<Boolean>().apply {
                postValue(false)
            }
        }
        declare {
            viewModel(override = true) {
                mockViewModel
            }
        }

        ActivityScenario.launch<DetailActivity>(
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                DetailActivity::class.java
            ).apply {
                putExtra(MOVIE_EXTRA_TAG, element)
            }
        )

        //test1: correct graphic is set
        onView(withId(R.id.favFab)).check(matches(drawableIsCorrect(R.drawable.ic_favorite_border_black_24dp)))

        //test2 correct behaviour
        onView(withId(R.id.favFab)).perform(click())
        verify(exactly = 1) { mockViewModel.addToFavorites(titleStr) }
        verify(exactly = 0) { mockViewModel.removeFromFavorites(titleStr) }
    }

    @Test
    fun clickOnPosterViewLaunchesPosterActivity() {
        val element = Movie("", 0, "", 0, 0, "")
        intentsTestRule.launchActivity(
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                DetailActivity::class.java
            ).apply {
                putExtra(MOVIE_EXTRA_TAG, element)
            }
        )

        onView(withId(R.id.posterImgView)).perform(click())

        intended(IntentMatchers.hasComponent(PosterActivity::class.qualifiedName))
    }

    @Test
    fun longClickOnTitleCopiedTitleToClipBoard() {
        val element = Movie("title", 0, "", 0, 0, "")
        intentsTestRule.launchActivity(
            Intent(
                InstrumentationRegistry.getInstrumentation().targetContext,
                DetailActivity::class.java
            ).apply {
                putExtra(MOVIE_EXTRA_TAG, element)
            }
        )

        onView(withId(R.id.titleTV)).perform(longClick())

        val clipboardManager = intentsTestRule.activity.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        assertEquals(1, clipboardManager.primaryClip?.itemCount)
        assertEquals(element.name, clipboardManager.primaryClip?.getItemAt(0)?.text)
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(ViewMatchers.withText(
                InstrumentationRegistry.getInstrumentation().targetContext.getText(R.string.title_copied).toString()
            )))
    }


}