package com.mongsil.mongsildiary.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongsil.mongsildiary.utils.printLog

class RecordViewModel : ViewModel() {
    private val _contents = MutableLiveData<String>()
    val contents: LiveData<String>
        get() = _contents

    override fun onCleared() {
        super.onCleared()
        "++onCleared()".printLog("RecordViewModel")
    }

    fun setContents(data : String){
        this._contents.value = data
    }
}