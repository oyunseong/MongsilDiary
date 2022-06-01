package com.mongsil.mongsildiary.data.database

import androidx.room.TypeConverter
import com.mongsil.mongsildiary.data.database.entity.TimeSlot

class Converters {
    @TypeConverter
    fun toTimeSlot(value: String): Enum<*> = enumValueOf<TimeSlot>(value)

    @TypeConverter
    fun fromTimeSlot(value: TimeSlot) = value.name
}