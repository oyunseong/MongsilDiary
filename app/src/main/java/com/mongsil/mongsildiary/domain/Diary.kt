package com.mongsil.mongsildiary.domain

import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.emoticons
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.ui.home.today.Emoticon

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

val defaultSlotList = listOf<Slot>(
    Slot(
        20220629, "하루끝",
        TimeSlot.EndOfTheDay,
        Emoticon(
            id = 2,
            image = R.drawable.ic_emoticon_03,
            name = "노랑"
        ),
    )
)
