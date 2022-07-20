package com.mongsil.mongsildiary.data.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * 데이터 항목
 * */

@Entity(primaryKeys = ["date", "timeSlot"])
data class SlotEntity(
    val date: Long,
    val timeSlot: TimeSlot,
    @ColumnInfo val emoticonId: Int = -1,
    @ColumnInfo var text: String = ""
)

@Entity
data class RecordEntity(
    @PrimaryKey
    val date: Long,
    val text: String = ""
//    val imageUrls: List<String>?
)

@Parcelize
enum class TimeSlot : Parcelable {
    Morning, Launch, Dinner, EndOfTheDay, Advertisement
}