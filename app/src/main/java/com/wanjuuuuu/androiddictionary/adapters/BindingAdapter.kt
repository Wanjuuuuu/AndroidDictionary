package com.wanjuuuuu.androiddictionary.adapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isSelected")
fun setIsSelected(view: View, isSelected: Boolean) {
    view.isSelected = isSelected
}