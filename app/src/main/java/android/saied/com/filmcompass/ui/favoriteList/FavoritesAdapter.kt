package android.saied.com.filmcompass.ui.favoriteList

import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.R
import android.saied.com.filmcompass.utils.getColor
import android.saied.com.filmcompass.utils.metaScoreString
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
        } ?: Unit
}
