package com.saied.dvdprism.app.ui.movieList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.ui.main.IMainViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

abstract class MovieListFragment : Fragment() {

    protected val viewModel by sharedViewModel<IMainViewModel>()
    protected val adapter: MovieListAdapter by lazy { MovieListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindMovieList()
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@MovieListFragment.adapter
        }
    }

    abstract fun bindMovieList()
}

class LatestListFragment : MovieListFragment() {
    override fun bindMovieList() {
        viewModel.latestLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}

class UpcomingListFragment : MovieListFragment() {
    override fun bindMovieList() {
        viewModel.upcommingLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
