package com.tapark.military_quest

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*

fun getScreenWidth(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.width() - insets.left - insets.right
    } else {
        val displayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.widthPixels
    }
}

fun getScreenHeight(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics = wm.currentWindowMetrics
        val insets = windowMetrics.windowInsets
            .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
        windowMetrics.bounds.height() - insets.bottom - insets.top
    } else {
        val displayMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
}

fun dpToPx(context: Context, dp: Int): Int {
    val density = context.resources.displayMetrics.density
    return (dp * density).toInt()
}

fun pxToDp(context: Context, px: Int): Int {
    val density = context.resources.displayMetrics.density
    return (px / density).toInt()
}

fun milliToYmd(milli: Long): String {
    val locale = Locale.getDefault()
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", locale)

    return dateFormat.format(Date(milli))
}

fun ymdToMilli(ymd: String): Long {
    val locale = Locale.getDefault()
    val dateFormat = SimpleDateFormat("yyyy.MM.dd", locale)

    return dateFormat.parse(ymd)?.time ?: System.currentTimeMillis()
}