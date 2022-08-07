package com.wanjuuuuu.androiddictionary.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children

class TouchDelegateViewGroup : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        super.setOnClickListener(onClickListener)
        delegateClickEventOfChildren(onClickListener)
    }

    private fun delegateClickEventOfChildren(onClickListener: OnClickListener?) {
        val viewGroup = this as ViewGroup
        for (child in viewGroup.children) {
            child.setOnClickListener(onClickListener)
        }
    }
}