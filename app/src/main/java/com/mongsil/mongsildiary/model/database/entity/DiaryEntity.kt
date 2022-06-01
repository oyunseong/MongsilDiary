package com.mongsil.mongsildiary.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["date", "timeSlot"])
data class SlotEntity(
    val date: Long,
    val timeSlot: TimeSlot,
    val emoticonId: Int,
    val text: String
)

@Entity
data class RecordEntity(
    @PrimaryKey
    val date: Long,
    val text: String,
    val imageUrls: List<String>
)

enum class TimeSlot {
    Morning, Launch, Dinner
}