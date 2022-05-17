package com.mongsil.mongsildiary.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO 뷰모델은 Ui 패키지로
class TimeSlotViewModel : ViewModel() {
    private var _timeSlot = MutableLiveData<List<TimeSlot>>()
    val timeSlot: LiveData<List<TimeSlot>>
        get() = _timeSlot

    fun setTimeSlot(data : List<TimeSlot>){
//        val list = mutableListOf<TimeSlot>()
        // 정렬 등등 추가 예정
        this._timeSlot.value = data
    }
}