package com.mongsil.mongsildiary.model.database.entity

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Query("SELECT * FROM SlotEntity")
    fun getAllSlot(): Flow<List<SlotEntity>>

    @Query("SELECT * FROM SlotEntity WHERE date = :date")
    fun getSlotByDate(date: Long): Flow<List<SlotEntity>>

    @Query("SELECT * FROM RecordEntity")
    fun getAllRecord(): Flow<List<RecordEntity>>

    @Query("SELECT * FROM RecordEntity WHERE date = :date")
    fun getRecordByDate(date: Long): Flow<List<RecordEntity>>
}