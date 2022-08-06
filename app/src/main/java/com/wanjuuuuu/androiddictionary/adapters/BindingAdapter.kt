package com.wanjuuuuu.androiddictionary.adapters

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bookmarkSelected")
fun setBookmarkSelected(view: View, isSelected: Boolean) {
    view.isSelected = isSelected

    view.animation = null
    val scaleUpAnimation = ObjectAnimator.ofPropertyValuesHolder(
        view,
        PropertyValuesHolder.ofFloat("scaleX", 1.2f),
        PropertyValuesHolder.ofFloat("scaleY", 1.2f)
    ).apply { duration = 300 }
    val scaleBackAnimation = ObjectAnimator.ofPropertyValuesHolder(
        view,
        PropertyValuesHolder.ofFloat("scaleX", 1.0f),
        PropertyValuesHolder.ofFloat("scaleY", 1.0f)
    ).apply { duration = 300 }
    AnimatorSet().apply {
        play(scaleUpAnimation).before(scaleBackAnimation)
        start()
    }
}

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}