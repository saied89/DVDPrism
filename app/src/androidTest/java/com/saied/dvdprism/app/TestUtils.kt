package com.saied.dvdprism.app

import android.graphics.drawable.VectorDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object RecyclerViewMatchers {
    @JvmStatic
    fun hasItemCount(itemCount: Int): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("has $itemCount items")
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                return view.adapter?.itemCount == itemCount
            }
        }
    }

    @JvmStatic
    fun drawableIsCorrect(@DrawableRes drawableResId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with drawable from resource id: ")
                description.appendValue(drawableResId)
            }

            override fun matchesSafely(target: View?): Boolean {
                if (target !is ImageView) {
                    return false
                }
                if (drawableResId < 0) {
                    return target.drawable == null
                }
                val expectedDrawable = ContextCompat.getDrawable(target.context, drawableResId)
                    ?: return false

                val bitmap = (target.drawable as VectorDrawable).toBitmap()
                val otherBitmap = (expectedDrawable as VectorDrawable).toBitmap()
                return bitmap.sameAs(otherBitmap)
            }
        }
    }
}
