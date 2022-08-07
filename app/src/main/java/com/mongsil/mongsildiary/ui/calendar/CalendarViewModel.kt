package com.mongsil.mongsildiary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongsil.mongsildiary.utils.Date

class CalendarViewModel : ViewModel() {
    private var _date: MutableLiveData<String> = MutableLiveData(Date().date.toString())
    val date: LiveData<String> get() = _date

    init {
        _date.value = Date().date.toString()
    }

    fun setDate(date: String) {
        _date.value = date
    }
}