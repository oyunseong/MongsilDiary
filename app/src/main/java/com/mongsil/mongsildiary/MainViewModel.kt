package com.mongsil.mongsildiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.Date
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
):ViewModel() {
    private val emptyRecord = Record.mockRecord

    private val _recordData = MutableLiveData<Record>(emptyRecord)
    val recordData: LiveData<Record> get() = _recordData

    fun getRecordData() {
        viewModelScope.launch {
            try {
                _recordData.value = repository.getRecordByDate(Date().currentLongTypeDate()) ?: return@launch
            } catch (exception: Exception) {

            }
        }
    }

    fun deleteRecord() = viewModelScope.launch {
        repository.deleteRecord(Date().currentLongTypeDate())
        _recordData.value = emptyRecord
    }
}