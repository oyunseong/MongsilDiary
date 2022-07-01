package com.mongsil.mongsildiary.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.mongsil.mongsildiary.data.database.AppDatabase
import com.mongsil.mongsildiary.data.database.DiaryDao
import com.mongsil.mongsildiary.data.database.entity.SlotEntity
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.mapper.toRecord
import com.mongsil.mongsildiary.mapper.toRecordEntity
import com.mongsil.mongsildiary.mapper.toSlotEntity
import com.mongsil.mongsildiary.mapper.toSlots
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalStateException

private const val DATABASE_NAME = "MongsilDatabase"

class DiaryRepository private constructor(context: Context) {

    private val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val diaryDao = database.diaryDao()

    companion object {
        private var INSTANCE: DiaryRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DiaryRepository(context)
            }
        }

        fun get(): DiaryRepository {
            return INSTANCE ?: throw IllegalStateException("DiaryRepository must be initialized!!")
        }
    }

    val slotEntity: Flow<List<SlotEntity>> = diaryDao.getSlotsByDate2()

    suspend fun getSlotsByDate(date: Long): LiveData<List<Slot>> {
        return diaryDao.getSlotsByDate(date).toSlots()
    }

    suspend fun getRecordByDate(date: Long): Record {
        return diaryDao.getRecordByDate(date).toRecord()
    }

    suspend fun insertSlot(slot: Slot) {
        diaryDao.insertSlot(slot.toSlotEntity())
    }

    suspend fun insertRecord(record: Record) {
        diaryDao.insertRecord(record.toRecordEntity())
    }

}