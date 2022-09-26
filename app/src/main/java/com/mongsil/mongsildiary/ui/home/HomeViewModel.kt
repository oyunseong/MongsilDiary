package com.mongsil.mongsildiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.domain.Saying
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.ui.home.today.DataProvider
import com.mongsil.mongsildiary.utils.Date
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private val _slotData: MutableLiveData<List<Slot>> = MutableLiveData(emptyList())
    val slotData: LiveData<List<Slot>> get() = _slotData

    private val _saying: MutableLiveData<Saying> = MutableLiveData()
    val saying: LiveData<Saying> get() = _saying

    init {
        getSlotData(CalendarDay.today())
        setSaying(saying.value ?: Saying(emptyMap()))
    }

    fun setSaying(saying: Saying) {
        _saying.value = saying
    }

    fun getSlotData(calendarDay: CalendarDay) {
        viewModelScope.launch {
            val date = Date().convertCalendarDayToLong(calendarDay)
            val arraySlots = DataProvider.getDefaultSlotList(date)
            val slots = repository.getSlotsByDate(date)
            slots.forEach {
                when (it.timeSlot) {
                    TimeSlot.Morning -> arraySlots[0] =
                        it.copy(date = date)
                    TimeSlot.Launch -> arraySlots[1] =
                        it.copy(date = date)
                    TimeSlot.Dinner -> arraySlots[2] =
                        it.copy(date = date)
//                    TimeSlot.Advertisement -> arraySlots[3] =
//                        it.copy(date = date)
                    TimeSlot.EndOfTheDay -> arraySlots[3] =
                        it.copy(date = date)
                }
            }
            _slotData.value = arraySlots.toList()
        }
    }
}
