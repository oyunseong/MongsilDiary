package com.mongsil.mongsildiary.ui.home.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.printLog
import kotlinx.coroutines.launch

class TodayEmoticonViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private var _emoticons = MutableLiveData<List<Emoticon>>()
    val emoticons get() = _emoticons

    private var _emoticonState = MutableLiveData<List<TodayEmoticon>>()
    val emoticonState get() = _emoticonState

    private var _slotData = MutableLiveData<List<Slot>>()
    val slotData get() = _slotData

    // 번들로부터 넘겨받은 date(날짜)
    //                  timeSlot(아침점심저녁)
    private val _date = MutableLiveData<Long>()
    val date get() = _date

    private var _timeSlot = MutableLiveData<String>()
    val timeSlot: LiveData<String> get() = _timeSlot

    fun setDate(date: Long) {
        this._date.value = date
    }

    fun setTimeSlot(timeSlot: String) {
        this._timeSlot.value = timeSlot
    }

    val emoticonsCount = 0

    init {
        updateEmoticons()
        getSlotData()
    }

    private fun getSlotData() {
//        viewModelScope.launch {
////            val slots = repository.getSlotByDateAndTimeSlot(date, timeSlot)
//
//            _slotData.value = slots
//        }
    }

    private fun updateEmoticons() {
//        val emoticonList = mutableListOf<Emoticon>()
        val emoticonList = mutableListOf<TodayEmoticon>()

        for (i in emoticonList) {
//            emoticonList.add(TodayEmoticon(Emoticon(R.drawable.ic_emoticon_01, i"$i item"), false))
        }
        _emoticonState.value = emoticonList
    }

    fun insert(slot: Slot) = viewModelScope.launch {
//        repository.insertSlot()
    }

    override fun onCleared() {
        super.onCleared()
        "++onCleared".printLog("TodayEmoticonViewModel")
    }
}