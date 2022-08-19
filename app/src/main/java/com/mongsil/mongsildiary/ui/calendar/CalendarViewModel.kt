package com.mongsil.mongsildiary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
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

    private val _eventList: MutableLiveData<ArrayList<CalendarDay>> =
        MutableLiveData(ArrayList<CalendarDay>())
    val eventList: LiveData<ArrayList<CalendarDay>> get() = _eventList

    private var _slotDataMap = MutableLiveData<Map<Int, Int>>()
    val slotDataMap: LiveData<Map<Int, Int>> get() = _slotDataMap

    init {
        setSlotData()
    }

    private fun setSlotData() {
        viewModelScope.launch {
            _slotData.value = repository.getSlotDataAll()
        }
    }

    fun getData() {
        val arrayList = ArrayList<CalendarDay>()
        slotData.value!!.forEach {
            arrayList.add(CalendarDay.from(Date().convertLongToLocalDate(it.date)))
        }
        _eventList.value = arrayList
    }
}


//            if (slotData.value != null) {
//                slotData.value!!.forEach { it ->
//                    _slotDataMap.value.get(it.emoticon.id) = _slotDataMap[it.emoticon.id]!!.plus(1)
//                }
//            }