package com.rsschool.quiz.ui.utils

import android.content.Context
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import com.rsschool.quiz.R

class MenuManager {
    companion object {
        private const val MENU_PREFS = "settings_preferences"
    }

    fun handleMenuItemClick(toolbar: Toolbar, context: Context) {
        val prefs = context.getSharedPreferences(MENU_PREFS, Context.MODE_PRIVATE)

        var isSwipeable: Boolean = prefs.getBoolean("swipe", false)
        var isClickable: Boolean = prefs.getBoolean("click", false)

        toolbar.menu.getItem(0).isChecked = isSwipeable
        toolbar.menu.getItem(1).isChecked = isSwipeable

        println("fun $isSwipeable prefs ${prefs.getBoolean("swipe", false)}")

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.swipeable -> {

                    if (isSwipeable) {
                        isSwipeable = false
                        it.isChecked = false
                        prefs.edit { putBoolean("swipe", false) }
                        Toast.makeText(context, "swipe false", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        isSwipeable = true
                        it.isChecked = true
                        prefs.edit { putBoolean("swipe", true) }
                        Toast.makeText(context, "swipe true", Toast.LENGTH_SHORT).show()
                    }

                    true
                }
                R.id.clickable -> {
                    it.isChecked = true
                    Toast.makeText(context, "click", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> throw IllegalArgumentException("wrong id onClick menu item")
            }
        }
    }
}