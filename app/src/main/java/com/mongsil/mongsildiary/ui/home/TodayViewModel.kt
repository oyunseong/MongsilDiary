package com.mongsil.mongsildiary.ui.home

import android.app.Application
import androidx.lifecycle.*
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.repository.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class TodayViewModel(
    application: Application
) : AndroidViewModel(application), CoroutineScope {

    companion object {
        class Factory(private val app: Application) : ViewModelProvider.NewInstanceFactory() {
            @Suppress("unchecked_cast")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TodayViewModel::class.java)) {
                    return TodayViewModel(app) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }


//    private val repository = DiaryRepository(AppDatabase.getInstance(application, viewModelScope))

//    private val slotData: LiveData<List<Slot>>

    private val repository = DiaryRepository.get()

    private val _slotData= MutableLiveData<List<Slot>>()
    val slotData: LiveData<List<Slot>>
        get() = _slotData

    init {

    }

    fun insert(slot: Slot) = viewModelScope.launch {
        repository.insertSlot(slot)
    }

    fun setContents(data: List<Slot>) {
        this._slotData.value = data
    }

    fun getSlotData() {
        slotData
    }

//    fun getSlotData(): LiveData<List<Slot>> =

//    val data1 = repository.getRecordByDate(100)
//    private val observableSlot:LiveData<List<Slot>>

    //    val slotEntity: LiveData<List<SlotEntity>> = repository.slotEntity.asLiveData()
}
