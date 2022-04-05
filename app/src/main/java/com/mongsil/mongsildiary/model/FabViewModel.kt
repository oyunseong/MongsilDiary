package com.mongsil.mongsildiary.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FabViewModel : ViewModel() {
    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean>
        get() = _state

    // true = visible
    // false = gone
    fun setFabState(value : Boolean){
        this._state.value = value
    }

}