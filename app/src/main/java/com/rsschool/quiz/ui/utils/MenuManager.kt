package com.rsschool.quiz.ui.utils

import android.content.Context
import android.content.SharedPreferences
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentLaunchBinding


/**
 *  Feature was comment cause angry reviewers   =)
 *  uncomment for fun
 */

/**
 * manage menu setting that responsible for scrolling
 */
class MenuManager {
    companion object {
        private const val MENU_PREFS = "settings_preferences"


        private var isSwipeable: Boolean = false
        private var isClickable: Boolean = false
    }
    private var _binding: FragmentLaunchBinding? = null
    private val mBinding get() = requireNotNull(_binding)
    private lateinit var prefs: SharedPreferences

    fun handleMenuItemClick(binding: FragmentLaunchBinding) {
        _binding = binding
        val context = binding.root.context
        val toolbar = binding.toolbar
        prefs = context.getSharedPreferences(MENU_PREFS, Context.MODE_PRIVATE)

        isSwipeable = prefs.getBoolean("swipe", false)
        isClickable = prefs.getBoolean("click", false)

        if (isSwipeable) {
            onCheckSwipe(toolbar.menu.getItem(0))
        } else {
            onUncheckSwipe(toolbar.menu.getItem(0))
        }
        if (isClickable) {
            onCheckClicks(toolbar.menu.getItem(1))
        } else {
            onUncheckClicks(toolbar.menu.getItem(1))
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.swipeable -> {
                    if (isSwipeable) {
                        onUncheckSwipe(it)
                    } else {
                        onCheckSwipe(it)
                        showDialog(binding)
                    }
                }
                R.id.clickable -> {
                    if (isClickable) {
                        onUncheckClicks(it)
                    } else {
                        onCheckClicks(it)
                        showDialog(binding)
                    }
                }
                else -> throw IllegalArgumentException("wrong id onClick menu item")
            }


            true
        }
    }


    private fun onCheckSwipe(item: MenuItem) {
//        isSwipeable = true
//        item.isChecked = true
//        prefs.edit { putBoolean("swipe", true) }
//        mBinding.viewPager.isUserInputEnabled = true



        //delete then
        Toast.makeText(mBinding.root.context, "Uncomment MenuManager", Toast.LENGTH_LONG).show()
    }

    private fun onUncheckSwipe(item: MenuItem) {
//        isSwipeable = false
//        item.isChecked = false
//        prefs.edit { putBoolean("swipe", false) }
//        mBinding.viewPager.isUserInputEnabled = false
    }

    private fun onCheckClicks(item: MenuItem) {
//        isClickable = true
//        item.isChecked = true
//        prefs.edit { putBoolean("click", true) }
//        mBinding.tabLayout.shouldEnableTabs = true
    }

    private fun onUncheckClicks(item: MenuItem) {
//        isClickable = false
//        item.isChecked = false
//        prefs.edit { putBoolean("click", false) }
//        mBinding.tabLayout.shouldEnableTabs = false
    }
    private fun showDialog(binding: FragmentLaunchBinding){
//        MaterialAlertDialogBuilder(binding.root.context).apply {
//            setTitle("Warning")
//            setMessage("\"This feature don't allowed by task, also bugs can appear\"")
//            setPositiveButton("Ok I'm agree"){ dialog, which ->
//
//            }
//        }.show()
    }

}