package com.mongsil.mongsildiary

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Saying
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.server.GithubAPI
import com.mongsil.mongsildiary.server.RetrofitClient
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.printLog
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit

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