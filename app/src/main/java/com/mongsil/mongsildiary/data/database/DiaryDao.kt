package com.mongsil.mongsildiary.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mongsil.mongsildiary.data.database.entity.RecordEntity
import com.mongsil.mongsildiary.data.database.entity.SlotEntity
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
//    @Query("SELECT * FROM SlotEntity")
//    suspend fun getAllSlot(): List<SlotEntity>?

//    @Query("SELECT * FROM RecordEntity")
//    suspend fun getAllRecord(): List<RecordEntity>?

    @Query("SELECT * FROM RecordEntity WHERE date = :date")
    suspend fun getRecordByDate(date: Long): RecordEntity

    @Query("SELECT * FROM SlotEntity WHERE date = :date")
    suspend fun getSlotsByDate(date: Long): List<SlotEntity>

    @Query("SELECT * FROM SlotEntity SE WHERE SE.date = :date AND SE.timeSlot = :timeSlot")
    suspend fun getSlotByDateAndTimeSlot(date: Long, timeSlot: TimeSlot): SlotEntity

    @Query("SELECT * FROM SlotEntity")
    suspend fun getSlotsByDate2(): List<SlotEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSlot(slotEntity: SlotEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(recordEntity: RecordEntity)


}