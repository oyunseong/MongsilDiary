package com.mongsil.mongsildiary.utils

import android.annotation.SuppressLint
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat

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

    fun currentDateTypeString(): List<String> {
        val today: Long = System.currentTimeMillis() // long type 현재 시간
        val splitDate = sdfCalendarType.format(today).split("-")
        return splitDate
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
    fun convertLocalDateToLong(date: LocalDate): Long {
        val splitDate = date.toString().split("-")
        return concat(splitDate[0], splitDate[1], splitDate[2]).toLong()
    }

    fun convertLongToLocalDate(date: Long): LocalDate {
        val strDate = date.toString()
        val year = concat(
            strDate[0].toString(),
            strDate[1].toString(),
            strDate[2].toString(),
            strDate[3].toString()
        ).toInt()
        val month = concat(
            strDate[4].toString(),
            strDate[5].toString()
        ).toInt()
        val day = concat(
            strDate[6].toString(),
            strDate[7].toString()
        ).toInt()
        return LocalDate.of(year, month, day)
    }

    fun convertCalendarDayToLong(date: CalendarDay): Long {
        val splitDate = date.date.toString().split("-")
        return concat(splitDate[0], splitDate[1], splitDate[2]).toLong()
    }

    fun removeDateOfDayFromCalendarDay(date: CalendarDay): String {
        val splitDate = date.date.toString().split("-")
        return concat(splitDate[0], splitDate[1], "__")
    }

    fun plusDotCalendarDay(date: CalendarDay): String {
        val splitDate = date.date.toString().split("-")
        return concat(splitDate[0], ".", splitDate[1], ".", splitDate[2])
    }

    fun otherDayText(date: CalendarDay): String {
        val splitDate = date.date.toString().split("-")
        return concat(splitDate[0], "년 ", splitDate[1], "월 ", splitDate[2], "일")
    }
}

