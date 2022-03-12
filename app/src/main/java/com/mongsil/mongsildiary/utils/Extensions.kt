package com.mongsil.mongsildiary.utils

class Extensions {
    fun String.log(contents: String) {
        android.util.Log.d("extensions log function", contents);
    }

    fun String.log(tag: String, contents: String) {
        android.util.Log.d(tag, contents);
    }
}