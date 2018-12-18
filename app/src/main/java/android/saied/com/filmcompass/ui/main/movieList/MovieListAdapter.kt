package android.saied.com.filmcompass.ui.main.movieList

import android.saied.com.common.model.ScoreIndication
import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.R
import android.saied.com.filmcompass.ui.main.movieDetail.DetailActivity
import android.saied.com.moviefetcher.formatDate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie.*
import java.util.*

class MovieListAdapter : ListAdapter<Movie, MovieViewHolder>(movieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
            .let {
                MovieViewHolder(it)
            }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.run {
            val item = getItem(position)
            bind(item)
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
        posterImgView.setImageURI(item.posterUrl_250p)
        val indicationColor = when(item.metaIndication) {
            ScoreIndication.POSITIVE -> R.color.scorePositive
            ScoreIndication.MIXED -> R.color.scoreMixed
            ScoreIndication.NEGATIVE -> R.color.scoreNegative
            ScoreIndication.TBD -> R.color.scoreTBD
        }.let { colorRes ->
            ContextCompat.getColor(containerView!!.context, colorRes)
        }
        itemContainer.setBackgroundColor(indicationColor)
        scoreTV.text = if(item.metaScore != null) item.metaScore.toString() else "TBD"
        containerView?.setOnClickListener {
            DetailActivity.launchDetailActivityWithTransition(it.context, item, posterImgView)
        }
    }
}