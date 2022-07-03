package com.mongsil.mongsildiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.ui.home.today.Emoticon
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.printLog
import kotlinx.coroutines.launch

class TodayViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {
    private val _slotData = MutableLiveData<List<Slot>>(listOf())
    val slotData: LiveData<List<Slot>>
        get() = _slotData

    init {
        initData()
        getSlotData()
    }

    fun insert(slot: Slot) = viewModelScope.launch {
        repository.insertSlot(slot)
//        getSlotData()
        val currentSlotData = _slotData.value ?: emptyList()
        _slotData.value = currentSlotData + slot
    }

    private fun getSlotData() {
        viewModelScope.launch {
            val slots = repository.getSlotsByDate(Date().date)
            val slotsTest = repository.getSlotsByDate2()
            "slotsTest : $slotsTest".printLog("TodayViewModel")
            _slotData.value = slots
            "slots : $slots".printLog("getSlotData")
        }
    }

    private fun initData() {
        insert(
            Slot(
                Date().date, "아침",
                TimeSlot.Morning, Emoticon(
                    id = 2,
                    image = R.drawable.ic_emoticon_03,
                    name = "노랑"
                )
            )
        )
        insert(
            Slot(
                Date().date, "점심",
                TimeSlot.Launch, Emoticon(
                    id = 2,
                    image = R.drawable.ic_emoticon_03,
                    name = "노랑"
                )
            )
        )
        insert(
            Slot(
                Date().date, "저녁",
                TimeSlot.Dinner, Emoticon(
                    id = 2,
                    image = R.drawable.ic_emoticon_03,
                    name = "노랑"
                )
            )
        )
        insert(
            Slot(
                Date().date, "하루끝",
                TimeSlot.EndOfTheDay, Emoticon(
                    id = 2,
                    image = R.drawable.ic_emoticon_03,
                    name = "노랑"
                )
            )
        )
    }
}
