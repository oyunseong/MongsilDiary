package com.mongsil.mongsildiary.model

//TODO 프로퍼티들이 Nullable 이어야 하는 지 생각해보기.
data class TimeSlot(
    val text: String? = null,
    val img: Int? = null,
    val time: String? = null,
)