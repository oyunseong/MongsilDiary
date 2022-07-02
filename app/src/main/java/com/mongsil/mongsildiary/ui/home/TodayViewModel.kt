package com.mongsil.mongsildiary.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import kotlinx.coroutines.launch
import java.util.*

class TodayViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private val _slotData = MutableLiveData<List<Slot>>(listOf())
    val slotData: LiveData<List<Slot>>
        get() = _slotData

    init {
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
            val slots = repository.getSlotsByDate(Calendar.DAY_OF_MONTH.toLong())
            _slotData.value = slots
        }
    }
}
