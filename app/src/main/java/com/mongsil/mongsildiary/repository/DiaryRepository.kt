package com.mongsil.mongsildiary.repository

import com.mongsil.mongsildiary.data.database.DiaryDao
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.mapper.toRecord
import com.mongsil.mongsildiary.mapper.toRecordEntity
import com.mongsil.mongsildiary.mapper.toSlotEntity
import com.mongsil.mongsildiary.mapper.toSlots

class DiaryRepository(
    private val diaryDao: DiaryDao
) {

    suspend fun getSlotsByDate(date: Long): List<Slot> {
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