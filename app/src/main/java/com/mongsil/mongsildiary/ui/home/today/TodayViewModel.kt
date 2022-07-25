package com.mongsil.mongsildiary.ui.home.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import kotlinx.coroutines.launch

class TodayViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private var _emoticonState = MutableLiveData<List<Emoticon>>()
    val emoticonState: LiveData<List<Emoticon>> get() = _emoticonState

    private var _selectedEmotionState = MutableLiveData<Emoticon>()
    val selectedEmotionState: LiveData<Emoticon> get() = _selectedEmotionState

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

    fun selectEmoticon(emoticon: Emoticon) {
        _selectedEmotionState.value = emoticon
    }

    fun insert(slot: Slot) = viewModelScope.launch {
        repository.insertSlot(slot)
    }
}
