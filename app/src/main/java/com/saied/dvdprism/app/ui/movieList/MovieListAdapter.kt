package com.saied.dvdprism.app.ui.movieList

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.common.model.formatDate
import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.ui.movieDetail.DetailActivity
import com.saied.dvdprism.app.utils.getColor
import com.saied.dvdprism.app.utils.metaScoreString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.crashlytics.android.Crashlytics
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*
import java.util.*

class MovieListAdapter : PagedListAdapter<Movie, MovieViewHolder>(movieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
            .let {
                MovieViewHolder(it)
            }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.run {
            getItem(position)?.let {
                bind(it)
            }
        }
    }
}


private val movieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view), LayoutContainer {
    override val containerView: View? = view

    fun bind(item: Movie) {
        releaseDateTV.text = formatDate(Date(item.releaseDate))
        posterImgView.setImageURI(item.getPosterUrl250p())
        val indicationColor = item.getMetaIndication().getColor(containerView!!.context)
        itemContainer.setBackgroundColor(indicationColor)
        scoreTV.text = item.metaScoreString
        containerView.setOnClickListener {
            DetailActivity.launchDetailActivityWithTransition(it.context, item, posterImgView, scoreTV)
        }
    }
}
