package android.saied.com.filmcompass.ui.movieList


import android.os.Bundle
import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movielist.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModel()
    private val adapter: MovieListAdapter by lazy { MovieListAdapter() }

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movielist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView.apply { } // fix for wierd null exception error happening in showError
        viewModel.stateLiveData.observe(this, Observer {
            progressbar.visibility = if (it is MainState.Loading) View.VISIBLE else View.GONE
            snackbar?.dismiss()
            setData(it.movieList)
            when (it) {
                is MainState.Error -> {
                    showError(it.throwable)
                }
            }
        })
        recyclerView.run {
            layoutManager = GridLayoutManager(context, 2)
            adapter = this@MovieListFragment.adapter
//            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
        viewModel.refreshMovies()
    }

    private fun setData(movies: List<Movie>) {
        adapter.submitList(movies)
    }

    private fun showError(throwable: Throwable) {
        snackbar = Snackbar.make(rootView, throwable.message ?: "Unkown Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("retry") {
                viewModel.refreshMovies()
            }
        snackbar?.show()
    }
}
