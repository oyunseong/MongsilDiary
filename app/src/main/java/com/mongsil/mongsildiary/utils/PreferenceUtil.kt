package com.mongsil.mongsildiary.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    val PREFS_FILENAME = "allow_key"
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    fun getState(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }

    fun setState(key: String, defValue: Boolean) {
        prefs.edit().putBoolean(key, defValue).apply()
    }
}