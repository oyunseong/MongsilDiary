package com.mongsil.mongsildiary.repository

import com.mongsil.mongsildiary.data.database.DiaryDao
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.domain.Record
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.mapper.*
import com.mongsil.mongsildiary.utils.printLog

class DiaryRepository(private val diaryDao: DiaryDao) {

    suspend fun getSlotDataAll(): List<Slot> {
        return diaryDao.getSlotDataAll().toSlots()
    }

    suspend fun getRecordDataAll(): List<Record> {
        return diaryDao.getRecordDataAll().toRecords()
    }

    suspend fun getSlotsByDate(date: Long): List<Slot> {
        return diaryDao.getSlotsByDate(date).toSlots()
    }

    suspend fun getSlotsFindByWithoutDate(date: String): List<Slot> {
        return diaryDao.getSlotsBySimilarDate(date).toSlots()
    }

    suspend fun getRecordFindByWithoutDate(date: String): List<Record> {
        return diaryDao.getRecordBySimilarDate(date).toRecords()
    }

    suspend fun getSlotByDateAndTimeSlot(date: Long, timeSlot: TimeSlot): Slot {
        return diaryDao.getSlotByDateAndTimeSlot(date, timeSlot).toSlots()
    }

    suspend fun getRecordByDate(date: Long): Record {
        return diaryDao.getRecordByDate(date).toRecord().also {
            it.toString().printLog("getRecordByDate")
            "${date}".printLog("getRecordByDate's param date")
        }
    }

    suspend fun insertSlot(slot: Slot) {
        diaryDao.insertSlot(slot.toSlotEntity())
    }

    suspend fun insertRecord(record: Record) {
        diaryDao.insertRecord(record.toRecordEntity())
    }

    suspend fun deleteRecord(date: Long) {
        diaryDao.deleteRecordByDate(date)
    }

}