package com.saied.dvdprism.app.ui.main

import android.content.Context
import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.ui.movieList.LatestListFragment
import com.saied.dvdprism.app.ui.movieList.UpcomingListFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MovieListPagerAdapter(fragmentManager: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fragmentManager) {
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
            0 -> context.getString(R.string.latest)
            1 -> context.getString(R.string.upcoming)
            else -> null
        }
}