package com.mongsil.mongsildiary.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

//data class RecordWithSlots(
//    @Embedded val recordEntity: RecordEntity,
//    @Relation(
//        parentColumn = "date",
//        entityColumn = "date"
//    )
//    val slotEntity: List<SlotEntity>,
//)

@Entity(primaryKeys = ["date", "timeSlot"])
data class SlotEntity(
    val date: Long,
    val timeSlot: TimeSlot,
    val emoticonId: Int = -1,
    val text: String = ""
)

@Entity
data class RecordEntity(
    @PrimaryKey
    val date: Long,
    val text: String = "",
//    val imageUrls: List<String>?
)

enum class TimeSlot {
    Morning, Launch, Dinner
}