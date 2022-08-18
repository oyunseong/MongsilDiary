package com.mongsil.mongsildiary.domain

import android.os.Parcelable
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.data.database.entity.TimeSlot
import com.mongsil.mongsildiary.ui.home.today.DataProvider
import com.mongsil.mongsildiary.ui.home.today.Emoticon
import com.mongsil.mongsildiary.utils.Date
import kotlinx.parcelize.Parcelize


/**
 * @param date : 날짜
 * @param text : 내용
 * @param timeSlot :  slot의 시간 (아침, 점심, 저녁, 하루끝)
 * @param emoticon : 이모티콘의 정보
 * */

@Parcelize
data class Slot(
    val date: Long,
    val text: String,
    val timeSlot: TimeSlot,
    val emoticon: Emoticon,
) : Parcelable {
    companion object {
        val mockSlot = Slot(
            date = 100,
            text = "text",
            timeSlot = TimeSlot.Morning,
            emoticon = DataProvider.getEmoticonList()[0]
        )
    }
}

@Parcelize
data class Record(
    val date: Long,
    val text: String = "",
) : Parcelable {
    companion object {
        val mockRecord = Record(
            date = 100,
            text = "",
        )
    }
}

val defaultSlotList = arrayOf(
    Slot(
        Date().currentLongTypeDate(), "",
        TimeSlot.Morning, Emoticon(
            id = 0,
            image = R.drawable.ic_emoticon_01,
            name = "노랑"
        )
    ),
    Slot(
        Date().currentLongTypeDate(), "",
        TimeSlot.Launch, Emoticon(
            id = 1,
            image = R.drawable.ic_emoticon_02,
            name = "분홍"
        )
    ),
    Slot(
        Date().currentLongTypeDate(), "",
        TimeSlot.Dinner, Emoticon(
            id = 2,
            image = R.drawable.ic_emoticon_03,
            name = "주황"
        )
    ),
    Slot(
        Date().currentLongTypeDate(), "",
        TimeSlot.Advertisement, Emoticon(
            id = 3,
            image = R.drawable.ic_emoticon_04,
            name = "다홍"
        )
    ),
    Slot(
        Date().currentLongTypeDate(), "",
        TimeSlot.EndOfTheDay, Emoticon(
            id = 4,
            image = R.drawable.ic_emoticon_05,
            name = "연보라"
        )
    )
)

