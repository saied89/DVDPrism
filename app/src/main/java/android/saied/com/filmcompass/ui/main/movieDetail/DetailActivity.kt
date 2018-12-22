package android.saied.com.filmcompass.ui.main.movieDetail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.net.Uri
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
import androidx.palette.graphics.Palette
import com.facebook.common.references.CloseableReference
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory
import com.facebook.imagepipeline.request.BasePostprocessor
import com.facebook.imagepipeline.request.ImageRequestBuilder
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
        metaScoreTV.text = movie.metaScoreString
        metaScoreTV.setBackgroundColor(movie.metaIndication.getColor(this))
        userScoreTV.text = movie.userScoreString
        userScoreTV.background = movie.userIndication.getUserScoreBG(this)
        setUpPoster(posterImgView, movie.posterUrl_250p)
    }

    private fun setUpPoster(target: SimpleDraweeView, uri: String) {
//        val controllerListener = object : BaseControllerListener<ImageInfo>(){
//
//            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
//                target.post {
//                    Palette.from(target.drawable.toBitmap())
//                        .generate()
//                        .lightMutedSwatch
//                        ?.rgb
//                        ?.let {
//                            toolbar.background = ColorDrawable(it)
//                        }
//                }
//            }
//
//        }

        val imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri))

        imageRequestBuilder.postprocessor = object : BasePostprocessor() {

            override fun process(
                sourceBitmap: Bitmap?,
                bitmapFactory: PlatformBitmapFactory?
            ): CloseableReference<Bitmap> {
                val bitmapRef =
                    if(sourceBitmap != null && bitmapFactory != null)
                        bitmapFactory.createBitmap(sourceBitmap.width, sourceBitmap.height)
                    else null

                return try {
                    val bitmap = bitmapRef?.get()
                    target.post {
                        Palette.from(bitmap!!)
                            .generate()
                            .lightMutedSwatch
                            ?.rgb
                            ?.let {
                                toolbar.background = ColorDrawable(it)
                            }
                    }
                    CloseableReference.cloneOrNull(bitmapRef)!!
                } finally {
                    CloseableReference.closeSafely(bitmapRef)
                }
            }
        }

        val imageRequest = imageRequestBuilder.build()
//        val controller = Fresco.newDraweeControllerBuilder()
//            .setImageRequest(imageRequest)
//            .setControllerListener(controllerListener)
//            .build()
        target.setImageRequest(imageRequest)
//        posterImgView.controller = controller
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
