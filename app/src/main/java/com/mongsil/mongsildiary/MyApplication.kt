package com.mongsil.mongsildiary

import android.app.Application
import com.mongsil.mongsildiary.utils.PreferenceUtil

// TODO 네이밍 생각해보기..
class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}