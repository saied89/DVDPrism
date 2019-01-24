package android.saied.com.filmcompass.utils

import android.app.Activity
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.SharedElementCallback
import com.facebook.drawee.view.SimpleDraweeView

fun Activity.fixExitShareElementCallback() {
    ActivityCompat.setExitSharedElementCallback(this, object : SharedElementCallback() {
        override fun onSharedElementEnd(
            sharedElementNames: List<String>,
            sharedElements: List<View>,
            sharedElementSnapshots: List<View>
        ) {
            super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
            for (view in sharedElements) {
                if (view is SimpleDraweeView) {
                    view.drawable.setVisible(true, true)
                }
            }
        }
    })
}