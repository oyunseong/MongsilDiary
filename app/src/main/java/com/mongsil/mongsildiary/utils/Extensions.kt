package com.mongsil.mongsildiary.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.mongsil.mongsildiary.data.database.entity.TimeSlot

fun String.printLog(tag: String? = null) {
    Log.d("+++ $tag", this)
}

fun Throwable.printError(tag: String? = null, message: String? = null) {
    Log.e("+++ $tag", "$message", this)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
    } else if (this == TimeSlot.Advertisement) {
        "광고"
    } else {
        ""
    }
}