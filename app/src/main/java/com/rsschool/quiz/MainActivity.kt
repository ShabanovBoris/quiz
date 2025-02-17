package com.rsschool.quiz

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         *  For properly insets work
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle("Warning")
            setMessage("If you quit your progress will be reset, you sure?")
            setNegativeButton(resources.getString(R.string.decline)) { dialog, which ->
                dialog.dismiss()
            }
            setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                super.onBackPressed()
            }
        }.show()
    }
}