package com.rsschool.quiz.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.tabs.TabLayout
import com.rsschool.quiz.R

class NonTouchableTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0
) : TabLayout(context, attrs, defaultAttrs) {

     var shouldEnableTabs: Boolean? = null
        set(value)  {
            field = value
            invalidate()
            requestLayout()
        }

    init {
        val attrsArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.NonTouchableTabLayout, 0, 0)
        try {
            shouldEnableTabs = attrsArray.getBoolean(
                R.styleable.NonTouchableTabLayout_allowClicks,
                false
            )
        } finally {
            attrsArray.recycle()
        }
    }


    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return shouldEnableTabs?.not() ?: true
    }
}