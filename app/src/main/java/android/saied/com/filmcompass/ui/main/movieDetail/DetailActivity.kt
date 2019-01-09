package android.saied.com.filmcompass.ui.main.movieDetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
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
import androidx.core.content.ContextCompat
import androidx.core.util.Pair
import androidx.palette.graphics.Palette
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
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
            setDisplayShowTitleEnabled(false)
        }
        titleTV.text = movie.name
        oneLinetitleTV.text = movie.name
        descriptionTV.text = movie.description
        metaScoreTV.text = movie.metaScoreString
        metaScoreTV.setBackgroundColor(movie.getMetaIndication().getColor(this))
        userScoreTV.text = movie.userScoreString
        userScoreTV.background = movie.getUserIndication().getUserScoreBG(this)
        posterImgView.setImageURI(movie.omdbDetails?.poster ?: movie.getPosterUrl250p())
        directorTV.text = movie.omdbDetails?.director
        runTimeTV.text = movie.omdbDetails?.runtime
        genreTV.text = movie.omdbDetails?.genre
        starringTV.text = movie.omdbDetails?.actors
        setUpPalette(movie.omdbDetails?.poster ?: movie.getPosterUrl250p())
    }

    private fun setUpPalette(uri: String) {
        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(ImageRequest.fromUri(uri), this)
        dataSource.subscribe(object : BaseBitmapDataSubscriber() {
            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                dataSource?.close()
            }

            override fun onNewResultImpl(bitmap: Bitmap?) {
                val palette = Palette.from(bitmap!!).generate()

                toolbar.background = palette.getLightMutedColor(
                    ContextCompat.getColor(this@DetailActivity, R.color.defaultColor)
                ).let {
                    ColorDrawable(it)
                }

                window.statusBarColor = palette.getDarkMutedColor(
                    ContextCompat.getColor(this@DetailActivity, R.color.defaultColorDark)
                )
                dataSource.close()
            }
        }, CallerThreadExecutor.getInstance())
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
