package com.mongsil.mongsildiary.ui.home.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.data.database.entity.SlotEntity
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.printLog
import kotlinx.coroutines.launch

class TodayViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private var _emoticonState = MutableLiveData<List<TodayEmoticon>>()
    val emoticonState: LiveData<List<TodayEmoticon>> get() = _emoticonState
    val emoticonsCount = 0

    private var _slotData = MutableLiveData<Slot>()
    val slotData: LiveData<Slot> get() = _slotData

    init {
        setEmoticons()
    }

    fun setSlot(slot: Slot) {
        this._slotData.value = slot
    }

    private fun setEmoticons() {
        val emoticonList = DataProvider.getEmoticonList()
        _emoticonState.value = emoticonList
    }

    fun insert(slot: Slot) = viewModelScope.launch {
        repository.insertSlot(slot)
    }
}
