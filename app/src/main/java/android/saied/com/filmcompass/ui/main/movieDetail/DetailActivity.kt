package android.saied.com.filmcompass.ui.main.movieDetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.saied.com.common.model.Movie
import android.saied.com.filmcompass.R
import android.view.View
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
        posterImgView.setImageURI(movie.posterUrl_250p)
    }

    companion object {
        fun launchDetailActivityWithTransition(context: Context, movie: Movie, view: View) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA_TAG, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, Pair(view, "poster"))
            context.startActivity(intent, options.toBundle())
        }
    }
}
