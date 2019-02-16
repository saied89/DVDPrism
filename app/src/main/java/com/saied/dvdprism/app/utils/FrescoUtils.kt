package com.saied.dvdprism.app.utils

import android.graphics.Bitmap
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest

object FrescoUtils {
    fun withBitmap(imageUri: String, block: (Bitmap?) -> Unit) {
        val imagePipeline = Fresco.getImagePipeline()
        val dataSource = imagePipeline.fetchDecodedImage(ImageRequest.fromUri(imageUri), this)
        dataSource.subscribe(object : BaseBitmapDataSubscriber() {
            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                dataSource?.close()
            }

            override fun onNewResultImpl(bitmap: Bitmap?) {
                block(bitmap)
                dataSource?.close()
            }
        }, CallerThreadExecutor.getInstance())
    }
}