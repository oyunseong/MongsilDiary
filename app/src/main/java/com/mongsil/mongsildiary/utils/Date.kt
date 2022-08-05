package com.mongsil.mongsildiary.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

class Date {
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyyMMdd")

    @SuppressLint("SimpleDateFormat")
    private val sdf_dot: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
    private val sdf_no_day: SimpleDateFormat = SimpleDateFormat("yyyyMM")
    private val sdf_no_day_dot: SimpleDateFormat = SimpleDateFormat("yyyy.MM")
    private val currentTime = Calendar.getInstance().time

    val date = sdf.format(Date()).toLong()
    val date_dot = sdf_dot.format(Date()).toString()

    fun removeDayOfDate(date: Long) = sdf_no_day.format(date)


}