package com.mongsil.mongsildiary.data.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import java.io.ByteArrayOutputStream

class Converters {
    @TypeConverter
    fun toTimeSlot(value: String): Enum<*> = enumValueOf<TimeSlot>(value)

    @TypeConverter
    fun fromTimeSlot(value: TimeSlot) = value.name

    // Bitmap -> ByteArray 변환
    @TypeConverter
    fun toByteArray(bitmap : Bitmap) : ByteArray?{
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    // ByteArray -> Bitmap 변환
    @TypeConverter
    fun toBitmap(bytes : ByteArray) : Bitmap?{
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}