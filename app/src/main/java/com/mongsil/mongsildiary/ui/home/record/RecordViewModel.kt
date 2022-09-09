package com.mongsil.mongsildiary.ui.home.record

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.repository.DiaryRepository
import kotlinx.coroutines.launch

class RecordViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private val _contents = MutableLiveData<Record>()
    val contents: LiveData<Record> get() = _contents

    private val _bitmap = MutableLiveData<Bitmap?>()
    val bitmap: LiveData<Bitmap?> get() = _bitmap

    fun setBitmapImage(bitmap: Bitmap) {
        _bitmap.value = bitmap
    }

    fun setRecord(record: Record) {
        _contents.value = record
    }

    fun insert(record: Record) = viewModelScope.launch {
        repository.insertRecord(record)
    }
}