package com.mongsil.mongsildiary.ui.home.today


import android.os.Parcelable
import com.mongsil.mongsildiary.R
import kotlinx.parcelize.Parcelize


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
            Emoticon(9, R.drawable.ic_emoticon_10, "몽실이", 0),
            Emoticon(10, R.drawable.ic_emoticon_11, "몽실이", 0),
            Emoticon(11, R.drawable.ic_emoticon_12, "몽실이", 0),
            Emoticon(12, R.drawable.ic_emoticon_13, "몽실이", 0),
            Emoticon(13, R.drawable.ic_emoticon_14, "몽실이", 0),
            Emoticon(14, R.drawable.ic_emoticon_15, "몽실이", 0),
            Emoticon(15, R.drawable.ic_emoticon_16, "몽실이", 0),
            Emoticon(16, R.drawable.ic_emoticon_17, "몽실이", 0),
            Emoticon(17, R.drawable.ic_emoticon_18, "몽실이", 0),
            Emoticon(18, R.drawable.ic_emoticon_19, "몽실이", 0),
            Emoticon(19, R.drawable.ic_emoticon_20, "몽실이", 0),
            Emoticon(20, R.drawable.ic_emoticon_21, "몽실이", 0),
            Emoticon(21, R.drawable.ic_emoticon_22, "몽실이", 0),
            Emoticon(22, R.drawable.ic_emoticon_23, "몽실이", 0),
            Emoticon(23, R.drawable.ic_emoticon_24, "몽실이", 0),
        )
    }
}