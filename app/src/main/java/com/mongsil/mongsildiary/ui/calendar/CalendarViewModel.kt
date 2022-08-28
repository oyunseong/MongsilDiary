package com.mongsil.mongsildiary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.ui.home.today.Emoticon
import com.mongsil.mongsildiary.utils.Date
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private val _slotData: MutableLiveData<List<Slot>> = MutableLiveData(emptyList())
    val slotData: LiveData<List<Slot>> get() = _slotData

    private val _recordData: MutableLiveData<List<Record>> = MutableLiveData(emptyList())
    val recordData: LiveData<List<Record>> get() = _recordData

    private val _eventList: MutableLiveData<HashMap<CalendarDay, Int>> =
        MutableLiveData(HashMap())
    val eventList: LiveData<HashMap<CalendarDay, Int>> get() = _eventList

    init {
        setSlotData(CalendarDay.today())
        setRecordData(CalendarDay.today())
    }

    fun setSlotData(date: CalendarDay) {
        viewModelScope.launch {
            _slotData.value =
                repository.getSlotsFindByWithoutDate(Date().removeDateOfDayFromCalendarDay(date))
        }
    }

    fun setRecordData(date: CalendarDay) {
        viewModelScope.launch {
            _recordData.value =
                repository.getRecordFindByWithoutDate(Date().removeDateOfDayFromCalendarDay(date))
        }
    }

    private var hashMap = HashMap<CalendarDay, Int>()
    fun getSlotData() {
        slotData.value!!.forEach {
            hashMap[CalendarDay.from(Date().convertLongToLocalDate(it.date))] = it.emoticon.id
        }
        _eventList.value = hashMap
    }

    fun getRecordData() {
        val defaultEmoticon = Emoticon(0, R.drawable.ic_emoticon_01, "몽실이", 0)
        recordData.value!!.forEach {
            hashMap[CalendarDay.from(Date().convertLongToLocalDate(it.date))] =
                defaultEmoticon.id
        }
        _eventList.value = hashMap
    }
}