package com.mongsil.mongsildiary.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TODO 네이밍 더 신경 쓰기.
//TODO 플로팅 버튼 상태 관리를 위한 뷰모델인 것 같은데, 플로팅 버튼이 MainActivity 에 있으니까, 이 뷰모델은 없애고, MainViewModel 에서 플로팅 버튼을 관리하는 것이 좋아보임.
class FabViewModel : ViewModel() {
    private val _state = MutableLiveData<Boolean>() //TODO 무슨 state 인지 네이밍 신경쓰기
    val state: LiveData<Boolean>
        get() = _state

    // true = visible
    // false = gone
    fun setFabState(value: Boolean) {
        this._state.value = value
    }

}