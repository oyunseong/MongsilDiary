package com.mongsil.mongsildiary

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.Date
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    )
) : ViewModel() {
    private val emptyRecord = Record.mockRecord

    private val _recordData = MutableLiveData<Record>()
    val recordData: LiveData<Record> get() = _recordData

    private val _date: MutableLiveData<CalendarDay> = MutableLiveData<CalendarDay>()
    val date: LiveData<CalendarDay> get() = _date

    private var _showProgressEvent = MutableSharedFlow<Event>()
    val showProgressEvent = _showProgressEvent.asSharedFlow()

    private lateinit var progressDialog: ProgressDialog

    sealed class Event {
        data class ShowProgress(val boolean: Boolean) : Event()
        data class HideProgress(val boolean: Boolean) : Event()
    }

    init {
        _date.value = CalendarDay.today()
        getRecordData()
    }

    private fun event(event: Event) {
        viewModelScope.launch {
            _showProgressEvent.emit(event)
        }
    }

    fun showProgress() {
        progressDialog.show()
    }

    fun hideProgress() {
        progressDialog.dismiss()
    }

    private fun setStateProgress(boolean: Boolean) {
        when (boolean) {
            true -> event(Event.ShowProgress(boolean))
            false -> event(Event.HideProgress(boolean))
        }
    }

    fun getRecordData() {
        viewModelScope.launch {
            try {
                _recordData.value = repository.getRecordByDate(
                    Date().convertCalendarDayToLong(date.value!!)
                )
            } catch (e: Exception) {
                _recordData.value = Record.mockRecord
                e.printStackTrace()
            }
        }
    }

    fun insertRecord(record: Record, context: Context) = runBlocking {
        progressDialog = ProgressDialog(context)
        setStateProgress(true)
        val insert = launch {
            repository.insertRecord(record)
        }
        insert.join()
        setStateProgress(false)
    }

    fun deleteRecord() = viewModelScope.launch {
        repository.deleteRecord(Date().convertCalendarDayToLong(date.value!!))
        _recordData.value = emptyRecord
    }

    fun setDate(date: CalendarDay) {
        _date.value = date
    }

}