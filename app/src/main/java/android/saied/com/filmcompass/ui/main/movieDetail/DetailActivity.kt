package android.saied.com.filmcompass.ui.main.movieDetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.R
import android.saied.com.filmcompass.utils.getColor
import android.saied.com.filmcompass.utils.getUserScoreBG
import android.saied.com.filmcompass.utils.metaScoreString
import android.saied.com.filmcompass.utils.userScoreString
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.activity_detail.*

private const val MOVIE_EXTRA_TAG = "MOVIE_EXTRA_TAG"

class DetailActivity : AppCompatActivity() {

    private val movie: Movie by lazy {
        intent.extras!![MOVIE_EXTRA_TAG] as Movie
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        this.title = movie.name
        titleTV.text = movie.name
        descriptionTV.text = movie.description
        posterImgView.setImageURI(movie.posterUrl_250p)
        metaScoreTV.text = movie.metaScoreString
        metaScoreTV.setBackgroundColor(movie.metaIndication.getColor(this))
        userScoreTV.text = movie.userScoreString
        userScoreTV.background = movie.userIndication.getUserScoreBG(this)
    }

    companion object {
        fun launchDetailActivityWithTransition(context: Context, movie: Movie, posterView: View, metascoreView: View) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA_TAG, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context as Activity,
                Pair(posterView, "poster"),
                Pair(metascoreView, "metascore")
            )
            context.startActivity(intent, options.toBundle())
        }
    }
}
