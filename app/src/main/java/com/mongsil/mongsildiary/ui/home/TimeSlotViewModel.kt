package com.mongsil.mongsildiary.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.model.Emoticon
import com.mongsil.mongsildiary.model.TimeSlot

//TODO 뷰모델은 Ui 패키지로
class TimeSlotViewModel : ViewModel() {

    private var _emoticons = MutableLiveData<List<Emoticon>>()
    val emoticons get() = _emoticons

    init {
        updateEmoticons()
    }

    private fun updateEmoticons() {
        _emoticons.value = listOf(
            Emoticon(R.drawable.ic_emoticon_01, "텍스트")
        )
    }

}