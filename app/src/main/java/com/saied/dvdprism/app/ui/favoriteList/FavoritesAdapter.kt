package com.saied.dvdprism.app.ui.favoriteList

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.ui.movieDetail.DetailActivity
import com.saied.dvdprism.app.utils.getColor
import com.saied.dvdprism.app.utils.metaScoreString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_favorite_movie.view.*

class FavoritesAdapter : RecyclerView.Adapter<FavoriteViewHolder>() {

    var data = emptyList<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_movie, parent, false)
            .let {
                FavoriteViewHolder(it)
            }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) = holder.bind(data[position])
}

class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    override val containerView: View? = itemView

    fun bind(movie: Movie): Unit =
        containerView?.run {
            posterImgView.setImageURI(movie.getPosterUrl250p())
            titleTV.text = movie.name
            runtimeTV.text = movie.omdbDetails?.runtime
            metaScoreTV.text = movie.metaScoreString
            metaScoreTV.setBackgroundColor(movie.getMetaIndication().getColor(context))
            setOnClickListener {
                DetailActivity.launchDetailActivityWithTransition(
                    context = context,
                    movie = movie,
                    posterView = posterImgView,
                    metascoreView = metaScoreTV
                )
            }
        } ?: Unit
}
