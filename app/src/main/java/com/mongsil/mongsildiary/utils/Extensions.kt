package com.mongsil.mongsildiary.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mongsil.mongsildiary.MainActivity
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.temporal.WeekFields
import java.util.*

fun String.printLog(tag: String? = null) {
    Log.d("+++ $tag", this)
}

fun Throwable.printError(tag: String? = null, message: String? = null) {
    Log.e("+++ $tag", "$message", this)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun findCurrentFragment(view: View?): Fragment? {
    return try {
        view?.findFragment()
    } catch (e: Exception) {
        (unwrap(view?.context ?: return null) as? MainActivity)?.getCurrentFragment()
    }
}

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

internal fun Context.getDrawableCompat(@DrawableRes drawable: Int) = ContextCompat.getDrawable(this, drawable)

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) = setTextColor(context.getColorCompat(color))

fun daysOfWeekFromLocale(): Array<DayOfWeek> {
    val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
    var daysOfWeek = DayOfWeek.values()
    // Order `daysOfWeek` array so that firstDayOfWeek is at index 0.
    // Only necessary if firstDayOfWeek != DayOfWeek.MONDAY which has ordinal 0.
    if (firstDayOfWeek != DayOfWeek.MONDAY) {
        val rhs = daysOfWeek.sliceArray(firstDayOfWeek.ordinal..daysOfWeek.indices.last)
        val lhs = daysOfWeek.sliceArray(0 until firstDayOfWeek.ordinal)
        daysOfWeek = rhs + lhs
    }
    return daysOfWeek
}

fun unwrap(wrappedContext: Context): Activity? {
    var context: Context? = wrappedContext
    while (context !is Activity && context is ContextWrapper) {
        context = context.baseContext
    }
    return context as? Activity
}

fun TimeSlot.converterTimeSlot(): String {
    return if (this == TimeSlot.Morning) {
        "아침"
    } else if (this == TimeSlot.Launch) {
        "점심"
    } else if (this == TimeSlot.Dinner) {
        "저녁"
    } else if (this == TimeSlot.EndOfTheDay) {
        "하루끝"
//    } else if (this == TimeSlot.Advertisement) {
//        "광고"
    } else {
        ""
    }
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}