package com.mongsil.mongsildiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.domain.Record
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

    private val emptyRecord = Record.mockRecord

    private val _slotData: MutableLiveData<List<Slot>> = MutableLiveData(emptyList())
    val slotData: LiveData<List<Slot>> get() = _slotData

    private val _recordData = MutableLiveData<Record>(emptyRecord)
    val recordData: LiveData<Record> get() = _recordData

    init {
        getSlotData()
        getRecordData()
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

    private fun getRecordData() {
        viewModelScope.launch {
            try {
                _recordData.value = repository.getRecordByDate(Date().date) ?: return@launch
            } catch (exception: Exception) {

            }
        }
    }

    fun deleteRecord() = viewModelScope.launch {
        repository.deleteRecord(Date().date)
        _recordData.value = emptyRecord
    }

    fun insert(record: Record) = viewModelScope.launch {
        repository.insertRecord(record)
    }

}
