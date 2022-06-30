package com.mongsil.mongsildiary.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.utils.printLog

class TodayViewModel : ViewModel() {
    private val _contents = MutableLiveData<Slot>()
    val contents: LiveData<Slot>
        get() = _contents

    fun setContents(data: String) {
//        this._contents.value = data
    }


}