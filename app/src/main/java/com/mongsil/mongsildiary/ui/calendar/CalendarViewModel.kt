package com.mongsil.mongsildiary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongsil.mongsildiary.utils.Date

class CalendarViewModel : ViewModel() {
    private var _date: MutableLiveData<Long> = MutableLiveData(Date().date)
    val date: LiveData<Long> get() = _date

    init {
        _date.value = Date().date
    }

    fun setDate(date: Long) {
        _date.value = date
    }

    fun d(date: Long) = Date().removeDayOfDate(date)

}