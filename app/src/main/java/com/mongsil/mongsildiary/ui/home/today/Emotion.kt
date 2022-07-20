package com.mongsil.mongsildiary.ui.home.today

import android.os.Parcelable
import com.mongsil.mongsildiary.R
import kotlinx.parcelize.Parcelize

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

object DataProvider {

    fun getEmoticonList(): List<Emoticon> {
        return listOf<Emoticon>(
            Emoticon(0, R.drawable.ic_emoticon_01, "몽실이", 0),
            Emoticon(1, R.drawable.ic_emoticon_02, "몽실이", 0),
            Emoticon(2, R.drawable.ic_emoticon_03, "몽실이", 0),
            Emoticon(3, R.drawable.ic_emoticon_04, "몽실이", 0),
            Emoticon(4, R.drawable.ic_emoticon_05, "몽실이", 0),
            Emoticon(5, R.drawable.ic_emoticon_06, "몽실이", 0),
            Emoticon(6, R.drawable.ic_emoticon_07, "몽실이", 0),
            Emoticon(7, R.drawable.ic_emoticon_08, "몽실이", 0),
            Emoticon(8, R.drawable.ic_emoticon_09, "몽실이", 0),
            // Data Augmentation
            Emoticon(9, R.drawable.ic_emoticon_01, "몽실이", 0),
            Emoticon(10, R.drawable.ic_emoticon_02, "몽실이", 0),
            Emoticon(11, R.drawable.ic_emoticon_03, "몽실이", 0),
            Emoticon(12, R.drawable.ic_emoticon_04, "몽실이", 0),
            Emoticon(13, R.drawable.ic_emoticon_05, "몽실이", 0),
            Emoticon(14, R.drawable.ic_emoticon_06, "몽실이", 0),
            Emoticon(15, R.drawable.ic_emoticon_07, "몽실이", 0),
            Emoticon(16, R.drawable.ic_emoticon_08, "몽실이", 0),
            Emoticon(17, R.drawable.ic_emoticon_09, "몽실이", 0),
        )
    }
}