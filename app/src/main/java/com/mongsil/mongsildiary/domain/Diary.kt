package com.mongsil.mongsildiary.domain

import com.mongsil.mongsildiary.base.emoticons
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.ui.home.today.Emoticon
import java.util.*

//data class Diary(
//    val date: Long,
//    val slotMap: Map<TimeSlot, Slot>,
//    val record: Record,
//)

data class Slot(
    val date: Long,
    val text: String,
    val timeSlot: TimeSlot,
    val emoticon: Emoticon,
) {
    companion object {
        val mockSlot = Slot(
            date = 100,
            text = "text",
            timeSlot = TimeSlot.Morning,
            emoticon = emoticons[0]
        )
    }
}

data class Record(
    val date: Long,
    val text: String = "",
    val images: List<String> = emptyList(),
) {

    companion object {
        val mockRecord = Record(
            date = 100,
            text = "text",
        )
    }
}
