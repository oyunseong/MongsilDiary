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

    private val _eventList: MutableLiveData<HashSet<CalendarDay>> =
        MutableLiveData(HashSet<CalendarDay>())
    val eventList: LiveData<HashSet<CalendarDay>> get() = _eventList

    private var _slotDataMap = MutableLiveData<Map<Int, Int>>()
    val slotDataMap: LiveData<Map<Int, Int>> get() = _slotDataMap

    init {
        setSlotData()
        setRecordData()
    }

    private fun setSlotData() {
        viewModelScope.launch {
            _slotData.value = repository.getSlotDataAll()
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
    private val hashSet = HashSet<CalendarDay>()
    fun getSlotData() {
        slotData.value!!.forEach {
            hashSet.add(CalendarDay.from(Date().convertLongToLocalDate(it.date)))
        }
        _eventList.value = hashSet
    }

    fun getRecordData() {
        recordData.value!!.forEach {
            hashSet.add(CalendarDay.from(Date().convertLongToLocalDate(it.date)))
        }
        _eventList.value = hashSet
    }
}


//            if (slotData.value != null) {
//                slotData.value!!.forEach { it ->
//                    _slotDataMap.value.get(it.emoticon.id) = _slotDataMap[it.emoticon.id]!!.plus(1)
//                }
//            }