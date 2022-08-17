package com.mongsil.mongsildiary.utils

import android.annotation.SuppressLint
import org.threeten.bp.LocalDate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class Date {
    @SuppressLint("SimpleDateFormat")
    private val sdfNoBarType = SimpleDateFormat("yyyyMMdd")

    @SuppressLint("SimpleDateFormat")
    private val dotYearMonthDayFormat: SimpleDateFormat = SimpleDateFormat("yyyy.MM.dd")

    @SuppressLint("SimpleDateFormat")
    val sdfCalendarType: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    @SuppressLint("SimpleDateFormat")
    private val noDotYearMonth: SimpleDateFormat = SimpleDateFormat("yyyyMM")

    @SuppressLint("SimpleDateFormat")
    val dotYearMonth: SimpleDateFormat = SimpleDateFormat("yyyy.MM")

    fun removeDayOfDate(value: String): String {
        var date = Date()
        try {
            date = dotYearMonthDayFormat.parse(value) as Date

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dotYearMonthDayFormat.format(date)
    }

    /**
     * 현재 날짜를 long Type으로 가져와서
     *
     * yyyy-MM-dd Long type 형식으로 변환하는 함수
     * */
    fun currentLongTypeDate(): Long {
        val today: Long = System.currentTimeMillis() // long type 현재 시간
        val splitDate = sdfCalendarType.format(today).split("-")
        return concat(splitDate[0], splitDate[1], splitDate[2]).toLong()
    }

    /**
     * 문자를 합치는 함수
     * 사용 예시)
     *
     * val apple:String
     *
     * apple = concat("a","p","p","L","e")
     *
     * */
    private fun concat(vararg s1: String): String {
        val sb = StringBuilder()
        for (s in s1) {
            sb.append(s)
        }
        return sb.toString()
    }

    /**
     * LocalDate Type을 Long Type으로 변환
     * */
    fun convertCalendarDayToLong(date: LocalDate): Long {
        val splitDate = date.toString().split("-")
        return concat(splitDate[0], splitDate[1], splitDate[2]).toLong()
    }
}

