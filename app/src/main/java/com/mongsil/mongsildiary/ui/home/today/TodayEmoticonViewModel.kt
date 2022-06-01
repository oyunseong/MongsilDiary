package com.mongsil.mongsildiary.ui.home.today

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mongsil.mongsildiary.R

class TodayEmoticonViewModel : ViewModel() {

    private var _emoticons = MutableLiveData<List<Emoticon>>()
    val emoticons get() = _emoticons

    private var _emoticonState = MutableLiveData<List<TodayEmoticon>>()
    val emoticonState get() = _emoticonState

    val emoticonsCount = 0


    init {
        updateEmoticons()
    }

    private fun updateEmoticons() {
//        val emoticonList = mutableListOf<Emoticon>()
        val emoticonList = mutableListOf<TodayEmoticon>()

        for (i in emoticonList) {
//            emoticonList.add(TodayEmoticon(Emoticon(R.drawable.ic_emoticon_01, i"$i item"), false))
        }
        _emoticonState.value = emoticonList

    }

}