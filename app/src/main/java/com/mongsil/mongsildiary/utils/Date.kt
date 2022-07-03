package com.mongsil.mongsildiary.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

class Date {
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyyMMdd")

    @SuppressLint("SimpleDateFormat")
    val sdf_dot: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

    val date = sdf.format(Date()).toLong()
    val date_dot = sdf_dot.format(Date()).toString()


}