package com.mongsil.mongsildiary

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.PreferenceUtil

// TODO 네이밍 생각해보기..
class MyApplication : Application() {

    companion object {
        lateinit var prefs: PreferenceUtil

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)

        context = applicationContext
    }
}