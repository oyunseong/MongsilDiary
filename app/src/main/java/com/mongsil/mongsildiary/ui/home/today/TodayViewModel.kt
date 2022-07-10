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
    //
    private var _emoticons = MutableLiveData<List<Emoticon>>()
    val emoticons get() = _emoticons

    private var _emoticonState = MutableLiveData<List<TodayEmoticon>>()
    val emoticonState get() = _emoticonState
    val emoticonsCount = 0

    private var _slotData = MutableLiveData<Slot>()
    val slotData: LiveData<Slot> get() = _slotData

    init {
        updateEmoticons()
    }

    // 번들로부터 넘겨받은
    // date(날짜)
    // timeSlot(아침점심저녁)
    private val _date = MutableLiveData<Long>()
    val date get() = _date

    private var _timeSlot = MutableLiveData<TimeSlot>()
    val timeSlot: LiveData<TimeSlot> get() = _timeSlot

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> get() = _text

    fun setDate(date: Long) {
        this._date.value = date
    }

    fun setTimeSlot(timeSlot: TimeSlot) {
        this._timeSlot.value = timeSlot
    }

    fun setText(text: String) {
        this._text.value = text
    }

    fun getSlotData() {
        viewModelScope.launch {
            val slots = repository.getSlotByDateAndTimeSlot(
                date.value ?: return@launch,
                timeSlot.value ?: return@launch
            )
            _slotData.value = slots
        }
    }

    private fun updateEmoticons() {
        val emoticonList = mutableListOf<Emoticon>()
//            val emoticonList = mutableListOf<TodayEmoticon>()
//
        for (i in emoticonList) {
//            emoticonList.add(TodayEmoticon(Emoticon(R.drawable.ic_emoticon_01, i"$i item"), false))
        }
//            _emoticonState.value = emoticonList
    }

    fun insert(slot: Slot) = viewModelScope.launch {
        repository.insertSlot(slot)
    }
}
