package com.mongsil.mongsildiary.domain

import android.graphics.Bitmap
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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
    val text: String,
    val image: Bitmap?
) : Parcelable {
    companion object {
        val mockRecord = Record(
            date = 100,
            text = "",
            image = null,
        )
    }
}

data class Saying(
    @SerializedName("sayingList")
    val sayingList: Map<Int, String>
)
