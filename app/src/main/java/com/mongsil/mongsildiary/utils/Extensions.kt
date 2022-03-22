package com.mongsil.mongsildiary.utils

import android.util.Log

fun String.log() {
    Log.d("extensions log function", this)
}

fun String.log(tag: String) {
    Log.d(tag, this)
}