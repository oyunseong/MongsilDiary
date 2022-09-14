package com.mongsil.mongsildiary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.Date
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {
    private val emptyRecord = Record.mockRecord

    private val _recordData = MutableLiveData<Record>(emptyRecord)
    val recordData: LiveData<Record> get() = _recordData

    private val _date: MutableLiveData<CalendarDay> = MutableLiveData<CalendarDay>()
    val date: LiveData<CalendarDay> get() = _date

    init {
        _date.value = CalendarDay.today()
        getRecordData()
    }

    fun getRecordData() {
        viewModelScope.launch {
            try {
                _recordData.value =
                    repository.getRecordByDate(Date().convertCalendarDayToLong(date.value!!))
            } catch (e: Exception) {
                _recordData.value = emptyRecord
            }
        }
    }

    fun deleteRecord() = viewModelScope.launch {
        repository.deleteRecord(Date().convertCalendarDayToLong(date.value!!))
        _recordData.value = emptyRecord
    }

    fun setDate(date: CalendarDay) {
        _date.value = date
    }

}