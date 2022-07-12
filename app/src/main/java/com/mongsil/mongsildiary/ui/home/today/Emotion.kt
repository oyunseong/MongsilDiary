package com.mongsil.mongsildiary.ui.home.today

import android.os.Parcelable
import com.mongsil.mongsildiary.R
import kotlinx.android.parcel.Parcelize

// TODO 아이템 클릭 효과
// 선택된 이모티콘을 저장하는 라이브데이터 선언
// 라이브데이터가 각 이모티콘 뷰홀더에서 관찰하면서 선택된 이모티콘이 자기자신이면 표시
// 뷰모델을 리사이클러뷰 어댑터로 보내고, onBindViewHolder에서 옵저버

@Parcelize
data class Emoticon(
    val id: Int = -1,
    val image: Int,
    val name: String,
    val price: Int = 0
) : Parcelable

@Parcelize
data class TodayEmoticon(
    val emoticon: Emoticon,
    val isSelected: Boolean = false
) : Parcelable

object DataProvider {

    fun getEmoticonList(): List<TodayEmoticon> {
        return listOf<TodayEmoticon>(
            TodayEmoticon(Emoticon(0, R.drawable.ic_emoticon_01, "몽실이", 0), false),
            TodayEmoticon(Emoticon(1, R.drawable.ic_emoticon_02, "몽실이", 0), false),
            TodayEmoticon(Emoticon(2, R.drawable.ic_emoticon_03, "몽실이", 0), false),
            TodayEmoticon(Emoticon(3, R.drawable.ic_emoticon_04, "몽실이", 0), false),
            TodayEmoticon(Emoticon(4, R.drawable.ic_emoticon_05, "몽실이", 0), false),
            TodayEmoticon(Emoticon(5, R.drawable.ic_emoticon_06, "몽실이", 0), false),
            TodayEmoticon(Emoticon(6, R.drawable.ic_emoticon_07, "몽실이", 0), false),
            TodayEmoticon(Emoticon(7, R.drawable.ic_emoticon_08, "몽실이", 0), false),
            TodayEmoticon(Emoticon(8, R.drawable.ic_emoticon_09, "몽실이", 0), false),
            // Data Augmentation
            TodayEmoticon(Emoticon(9, R.drawable.ic_emoticon_01, "몽실이", 0), false),
            TodayEmoticon(Emoticon(10, R.drawable.ic_emoticon_02, "몽실이", 0), false),
            TodayEmoticon(Emoticon(11, R.drawable.ic_emoticon_03, "몽실이", 0), false),
            TodayEmoticon(Emoticon(12, R.drawable.ic_emoticon_04, "몽실이", 0), false),
            TodayEmoticon(Emoticon(13, R.drawable.ic_emoticon_05, "몽실이", 0), false),
            TodayEmoticon(Emoticon(14, R.drawable.ic_emoticon_06, "몽실이", 0), false),
            TodayEmoticon(Emoticon(15, R.drawable.ic_emoticon_07, "몽실이", 0), false),
            TodayEmoticon(Emoticon(16, R.drawable.ic_emoticon_08, "몽실이", 0), false),
            TodayEmoticon(Emoticon(17, R.drawable.ic_emoticon_09, "몽실이", 0), false),
        )
    }
}