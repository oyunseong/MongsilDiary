package com.mongsil.mongsildiary.data.database.entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
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

@Entity(primaryKeys = ["date"])
data class RecordEntity(
    val date: Long,
    @ColumnInfo val text: String = "",
//    @ColumnInfo val imageUrls: List<Bitmap>? = null
)

@Parcelize
enum class TimeSlot : Parcelable {
    Morning, Launch, Dinner, EndOfTheDay, Advertisement
}