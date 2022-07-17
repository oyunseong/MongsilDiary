package com.mongsil.mongsildiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.domain.defaultSlotList
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.Date
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private val _slotData: MutableLiveData<List<Slot>> = MutableLiveData(emptyList())
    val slotData: LiveData<List<Slot>>
        get() = _slotData

    init {
        getSlotData()
    }

    fun insert(slot: Slot) = viewModelScope.launch {
        repository.insertSlot(slot)
    }

    private fun getSlotData() {
        viewModelScope.launch {
            val slots = repository.getSlotsByDate(Date().date)
            val arraySlots = defaultSlotList

            slots.forEach {
                when (it.timeSlot) {
                    TimeSlot.Morning -> arraySlots[0] = it
                    TimeSlot.Launch -> arraySlots[1] = it
                    TimeSlot.Dinner -> arraySlots[2] = it
                    TimeSlot.Advertisement -> arraySlots[3] = it
                    TimeSlot.EndOfTheDay -> arraySlots[4] = it
                }
            }

            _slotData.value = arraySlots.toList()
        }
    }

}
