package android.saied.com.filmcompass.ui.main.movieList

import android.saied.com.filmcompass.R
import android.saied.com.filmcompass.model.Movie
import android.saied.com.filmcompass.parsing.formatDate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_movie.view.*
import java.util.*

class MovieListAdapter : ListAdapter<Movie, MovieViewHolder>(movieDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
            .let {
                MovieViewHolder(it)
            }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.itemView.run {
            val item = getItem(position)
            titleTV.text = item.name
//            releaseDateTV.text = context.getText(R.string.release_date_pattern)
//                .toString()
//                .format(formatDate(Date(item.releaseDate)))
            releaseDateTV.text = formatDate(Date(item.releaseDate))
            posterImgView.setImageURI(item.posterUrl)
            scoreWrapper.setCardBackgroundColor(item.score.color)
            scoreTV.text = item.score.score.toString()
        }
}


private val movieDiffCallback = object : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)