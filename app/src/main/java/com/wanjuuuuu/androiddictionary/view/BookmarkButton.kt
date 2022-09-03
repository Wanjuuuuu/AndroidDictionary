package com.wanjuuuuu.androiddictionary.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageButton
import androidx.core.view.setPadding
import com.wanjuuuuu.androiddictionary.R

class BookmarkButton : FrameLayout {

    private lateinit var imageButton: ImageButton
    private var neverSetSelectedAfterBound = true

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        imageButton = createButton(context)
        addView(imageButton)

        locateAndSizeButton()
    }

    private fun createButton(context: Context): ImageButton {
        return ImageButton(context).apply {
            background = resources.getDrawable(R.drawable.bookmark_selector, null)
        }
    }

    private fun locateAndSizeButton() {
        val imageSize = resources.getDimensionPixelSize(R.dimen.bookmark_image_size)
        val paddingSize = resources.getDimensionPixelSize(R.dimen.bookmark_padding)
        imageButton.layoutParams = LayoutParams(imageSize, imageSize).apply {
            gravity = Gravity.CENTER
            setPadding(paddingSize)
        }
    }

    fun bindOnToggledListener(onToggled: (Boolean) -> Unit) {
        neverSetSelectedAfterBound = true

        setOnClickListener {
            isSelected = !isSelected
            onToggled(isSelected)
        }
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        super.setOnClickListener(onClickListener)
        imageButton.setOnClickListener(onClickListener)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        imageButton.isSelected = selected
        doAnimateSelectionIfSetSelectedMoreThanOnce()
    }

    private fun doAnimateSelectionIfSetSelectedMoreThanOnce() {
        if (neverSetSelectedAfterBound) {
            neverSetSelectedAfterBound = !neverSetSelectedAfterBound
        } else {
            animateSelection()
        }
    }

    private fun animateSelection() {
        imageButton.animation = null
        val scaleUpAnimation = ObjectAnimator.ofPropertyValuesHolder(
            imageButton,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        ).apply { duration = 300 }
        val scaleBackAnimation = ObjectAnimator.ofPropertyValuesHolder(
            imageButton,
            PropertyValuesHolder.ofFloat("scaleX", 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 1.0f)
        ).apply { duration = 300 }
        AnimatorSet().apply {
            play(scaleUpAnimation).before(scaleBackAnimation)
            start()
        }
    }
}