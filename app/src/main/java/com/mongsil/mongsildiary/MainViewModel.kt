package com.mongsil.mongsildiary

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.SingleLiveEvent
import com.mongsil.mongsildiary.utils.printLog
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class MainViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {
    private val emptyRecord = Record.mockRecord

    private val _recordData = MutableLiveData<Record>()
    val recordData: LiveData<Record> get() = _recordData

    private val _date: MutableLiveData<CalendarDay> = MutableLiveData<CalendarDay>()
    val date: LiveData<CalendarDay> get() = _date

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()


    init {
        _date.value = CalendarDay.today()
        getRecordData()
    }

    fun getRecordData() {
        viewModelScope.launch {
            try {
                _recordData.value = repository.getRecordByDate(
                    Date().convertCalendarDayToLong(date.value!!)
                )
            } catch (e: Exception) {
                e.printStackTrace() // stackTrace 검색
            }
        }
    }

    suspend fun emitEvent(event: Event) {
        try {
            _event.emit(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun <T> awaitEvent(event: EventDelegator<T>): T {
        if (event is Event) {
            emitEvent(event)
        }

        return withContext(coroutineContext) {
            event.result()
        }
    }

    fun insertRecord(record: Record) = viewModelScope.launch {
//        DialogEvent.tryEmit()
//        val isOk = awaitEvent(DialogEvent)
//        isOk.toString().printLog("isOk is value")

        // showProgress
        // 보여주는 동안 터치나 뒤로가기 막기

//        event.collect {
        var isOk = true
        if (isOk) {
            delay(2000)
            repository.insertRecord(record) // 3초 이상 소요되는 작업
            isOk = false
            emitEvent(ToastEvent)
        }
    }


    // hideProgress
    // 이벤트를 record Fragment로 보내서 popbackstack 실행,  검색: livedata 이벤트 처리


    fun deleteRecord() = viewModelScope.launch {
        repository.deleteRecord(Date().convertCalendarDayToLong(date.value!!))
        _recordData.value = emptyRecord
    }

    fun setDate(date: CalendarDay) {
        _date.value = date
    }

}