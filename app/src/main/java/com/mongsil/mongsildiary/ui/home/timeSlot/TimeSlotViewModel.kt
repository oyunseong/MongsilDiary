package com.mongsil.mongsildiary.ui.home.timeSlot

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongsil.mongsildiary.R

class TimeSlotViewModel : ViewModel() {

    private var _emoticons = MutableLiveData<List<Emoticon>>()
    val emoticons get() = _emoticons

    init {
        updateEmoticons()
    }

    private fun updateEmoticons() {
        val emoticonList = mutableListOf<Emoticon>()

        for (i in 0..37) {
            emoticonList.add(Emoticon(R.drawable.ic_emoticon_01, "$i item"))
        }
        _emoticons.value = emoticonList
    }

}