package android.saied.com.filmcompass.ui.movieList


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.saied.com.filmcompass.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movie_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class MovieListFragment : Fragment() {

    abstract val viewModel: MovieListViewModel
    private val adapter: MovieListAdapter by lazy { MovieListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@MovieListFragment.adapter
        }
        viewModel.moviesLiveData.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}

class LatestListFragment : MovieListFragment() {
    override val viewModel: LatestListViewModel by viewModel()
}

class UpcommingListFragment : MovieListFragment() {
    override val viewModel: UpcommingListViewModel by viewModel()
}
