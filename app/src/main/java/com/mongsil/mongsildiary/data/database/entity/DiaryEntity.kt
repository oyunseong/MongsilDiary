package com.mongsil.mongsildiary.data.database.entity

import androidx.room.*

//data class RecordWithSlots(
//    @Embedded val recordEntity: RecordEntity,
//    @Relation(
//        parentColumn = "date",
//        entityColumn = "date"
//    )
//    val slotEntity: List<SlotEntity>,
//)

/**
 * 데이터 항목
 * */

@Entity(primaryKeys = ["date", "timeSlot"])
data class SlotEntity(
    val date: Long,
    val timeSlot: TimeSlot,
    @ColumnInfo val emoticonId: Int = -1,
    @ColumnInfo val text: String = ""
)

@Entity
data class RecordEntity(
    @PrimaryKey
    val date: Long,
    val text: String = ""
//    val imageUrls: List<String>?
)

enum class TimeSlot {
    Morning, Launch, Dinner, EndOfTheDay
}