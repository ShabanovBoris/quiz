package com.rsschool.quiz.ui.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.material.tabs.TabLayout

class NonTouchableTabLayout: TabLayout {


        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

        override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
            return !isEnabled
        }


}