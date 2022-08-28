package com.mongsil.mongsildiary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
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
        setRecordData()
    }

    fun setSlotData(date : CalendarDay) {
        viewModelScope.launch {
//            _slotData.value = repository.getSlotDataAll()
            _slotData.value = repository.getSlotsFindByWithoutDate(Date().removeDateOfDayFromCalendarDay(date))
        }
    }

    private fun setRecordData() {
        viewModelScope.launch {
            _recordData.value = repository.getRecordDataAll()
        }
    }

    /**
     * 현재 전체 데이터를 조회하는 중
     * 특정 기간 데이터만 조회하는 방법으로 바꿀 필요있음
     * */
    private var hashMap = HashMap<CalendarDay, Int>()
    fun getSlotData() {
        slotData.value!!.forEach {
            hashMap[CalendarDay.from(Date().convertLongToLocalDate(it.date))] = it.emoticon.image
        }
        _eventList.value = hashMap
    }

    fun getRecordData() {
        recordData.value!!.forEach {
            // TODO record는 이모티콘이 없는데 어떻게 처리할지 생각!
//            hashMap[CalendarDay.from(Date().convertLongToLocalDate(it.date))]
        }
//        _eventList.value = hashMap
    }
}