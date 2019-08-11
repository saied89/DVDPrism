package com.saied.dvdprism.app.ui.main


import android.os.Bundle
import com.saied.dvdprism.common.model.ScoreIndication
import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.ui.favoriteList.FavoritesActivity
import android.view.*
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    @VisibleForTesting
    val viewModel: IMainViewModel by sharedViewModel()

    private val pagerAdapter: MovieListPagerAdapter by lazy { MovieListPagerAdapter(childFragmentManager, context!!) }

    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView.apply { } // fix for wierd null exception error happening in showError
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        viewModel.stateLiveData.observe(this, Observer {
            progressbar.visibility = if (it is MainState.Loading) View.VISIBLE else View.GONE
            snackbar?.dismiss()
            when (it) {
                is MainState.Error -> {
                    showError(it.throwable)
                }
            }
        })
        viewModel.refreshMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.favs -> {
                context?.let {
                    FavoritesActivity.launch(it)
                }
                true
            }
            R.id.filter -> {
                context?.let { cnx ->
                    MaterialDialog(cnx)
                        .listItemsSingleChoice(
                            R.array.filter_choices,
                            initialSelection = viewModel.stateLiveData.value?.minMetaIndication?.filterIndex() ?: 2
                        ) { _, i, _ ->
                            when (i) {
                                0 -> ScoreIndication.POSITIVE
                                1 -> ScoreIndication.MIXED
                                2 -> ScoreIndication.NEGATIVE
                                else -> IllegalStateException()
                            }.let {
                                val state = viewModel.stateLiveData.value?.mutateMetaScore(it as ScoreIndication)
                                viewModel.stateLiveData.value = state
                            }
                        }
                        .show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun showError(throwable: Throwable) {
        snackbar = Snackbar.make(rootView, throwable.message ?: "Unkown Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("retry") {
                viewModel.refreshMovies()
            }
        snackbar?.show()
    }


}

private fun ScoreIndication.filterIndex(): Int =
    when (this) {
        ScoreIndication.POSITIVE -> 0
        ScoreIndication.MIXED ->  1
        ScoreIndication.NEGATIVE -> 2
        else -> throw IllegalStateException()
    }
