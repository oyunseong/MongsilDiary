package com.mongsil.mongsildiary.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

class Date {
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyyMMdd")
    val date = sdf.format(Date()).toLong()

}