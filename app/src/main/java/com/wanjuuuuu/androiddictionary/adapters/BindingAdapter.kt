package com.wanjuuuuu.androiddictionary.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bookmarkSelected")
fun setBookmarkSelected(view: View, isSelected: Boolean) {
    view.isSelected = isSelected
}

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}