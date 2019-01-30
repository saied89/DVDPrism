package android.saied.com.filmcompass.ui.movieList

import android.saied.com.filmcompass.R
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.android.synthetic.main.fragment_movie_list.view.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieListFragmentTest {
    @Test fun testFragment() {
        launchFragmentInContainer<LatestListFragment>()

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }
}