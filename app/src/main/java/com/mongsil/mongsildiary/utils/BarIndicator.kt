package com.mongsil.mongsildiary.utils

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout

class BarIndicator : LinearLayout {

    private var mContext: Context? = null

    private var mDefaultBar: Int = 0
    private var mSelectBar: Int = 0

    private var imageBar: MutableList<ImageView> = mutableListOf()

    // 0.0dp 를 픽셀 단위로 바꿉니다.
    // bar 사이 간격
    private val temp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 0.0f, resources.displayMetrics
    )

    constructor(context: Context) : super(context) {
        mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
    }

    /**
     * Bar Indicator create
     * @param count 바의 총 개수
     * @param defaultBar 선택되지 않은 바의 이미지
     * @param selectBar 선택된 바의 이미지
     * @param position 선택된 바의 포지션
     */
    fun createIndicator(count: Int, defaultBar: Int, selectBar: Int, position: Int) {
        this.removeAllViews()   // Child view remove from ViewGroup

        mDefaultBar = defaultBar
        mSelectBar = selectBar

        for (i in 0 until count) {
            imageBar.add(ImageView(mContext).apply {
                setPadding(temp.toInt(), 0, temp.toInt(), 0)
            })
            this.addView(imageBar[i])
        }

        // index select, 현재 선택된 페이지 position 전달 받음
        selectBar(position)
    }

    /**
     * 선택된 바 표시
     * @param position
     */
    fun selectBar(position: Int) {
        /*
        * indices method
        * get() = 0..size - 1 = (i in 0 .. imageBar.size -1)
        * */
        for (i in imageBar.indices) {
            if (i == position) {
                imageBar[i].setImageResource(mSelectBar)
            } else {
                imageBar[i].setImageResource(mDefaultBar)
            }
        }
    }
}