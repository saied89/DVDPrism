package android.saied.com.filmcompass.ui.main

import android.saied.com.filmcompass.ui.movieList.LatestListFragment
import android.saied.com.filmcompass.ui.movieList.UpcomingListFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MovieListPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> {
                LatestListFragment()
            }
            1 -> {
                UpcomingListFragment()
            }
            else -> throw IllegalArgumentException()
        }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? =
        when (position) {
            0 -> "Latest"
            1 -> "Upcoming"
            else -> null
        }
}