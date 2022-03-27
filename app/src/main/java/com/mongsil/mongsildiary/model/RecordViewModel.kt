package com.mongsil.mongsildiary.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {
    val _contents = MutableLiveData<String>()
    val contents: LiveData<String>
        get() = _contents


}