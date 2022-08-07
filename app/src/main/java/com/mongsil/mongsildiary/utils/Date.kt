package com.mongsil.mongsildiary.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
//import java.util.*
import java.util.Date

class Date {
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyyMMdd")

    @SuppressLint("SimpleDateFormat")
    private val sdf_dot: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

    @SuppressLint("SimpleDateFormat")
    private val sdf_no_day: SimpleDateFormat = SimpleDateFormat("yyyyMM")

    @SuppressLint("SimpleDateFormat")
    val sdfRemoveDayOfDate: SimpleDateFormat = SimpleDateFormat("yyyy.MM")

    val date = sdf.format(Date()).toLong()
    val date_dot = sdf_dot.format(Date())


    fun removeDayOfDate(value: String): String {
        var date = Date()
        try {
            date = sdfRemoveDayOfDate.parse(value) as Date

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return sdfRemoveDayOfDate.format(date)
    }

}