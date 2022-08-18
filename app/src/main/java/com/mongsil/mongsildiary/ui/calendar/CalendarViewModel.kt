package com.mongsil.mongsildiary.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import com.mongsil.mongsildiary.utils.Date
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val repository: DiaryRepository = DiaryRepository(
        AppDatabase.getInstance(MyApplication.context).diaryDao()
    ),
) : ViewModel() {

    private val _slotData: MutableLiveData<List<Slot>> = MutableLiveData(emptyList())
    val slotData: LiveData<List<Slot>> get() = _slotData

    private val map = mutableMapOf<Int, Int>()

    // TODO 년월을 넘겨주면 DB조회
    init {
        getData()
    }

    // TODO 해당 달 조회하고 이모티콘 개수 세서 세팅
    private fun getData() {

        viewModelScope.launch {
            try {
//                _slotData.value =
//                    date.value?.let { repository.getSlotsFindByWithoutDate("${it}__") }
//                        ?: return@launch

                if (slotData.value != null) {
                    slotData.value!!.forEach {
                        map[it.emoticon.id] = map[it.emoticon.id]!!.plus(1)
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
