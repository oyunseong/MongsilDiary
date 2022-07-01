package com.mongsil.mongsildiary

import android.app.Application
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.PreferenceUtil

// TODO 네이밍 생각해보기..
class MyApplication : Application() {
//    val database by lazy { AppDatabase.getInstance(this) }
//    val repository by lazy { DiaryRepository(database.diaryDao()) }

    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        DiaryRepository.initialize(this)
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}