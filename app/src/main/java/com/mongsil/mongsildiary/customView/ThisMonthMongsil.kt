package com.mongsil.mongsildiary.customView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.ThisMonthMongsilBinding

class ThisMonthMongsil : ConstraintLayout {
    private lateinit var binding: ThisMonthMongsilBinding

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
        getAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs) {
        initView()
        getAttrs(attrs, defStyle)
    }

    private fun initView() {
        binding = ThisMonthMongsilBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        super.onDraw(canvas)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ThisMonthMongsil)
        setTypedArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ThisMonthMongsil, defStyle, 0)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        setBackground(typedArray)
        setCount(typedArray)
        setProgress(typedArray)
        setEmoticon(typedArray)

        typedArray.recycle()
    }

    private fun setCount(typedArray: TypedArray) {
        val count = typedArray.getText(R.styleable.ThisMonthMongsil_count)
        binding.count.text = "$count"
    }

    private fun setProgress(typedArray: TypedArray) {
        val process = typedArray.getInt(R.styleable.ThisMonthMongsil_progress, 0)

        binding.progress.progress = process
    }

    private fun setEmoticon(typedArray: TypedArray) {
        val emoticon = typedArray.getDrawable(
            R.styleable.ThisMonthMongsil_emoticons
        )
        binding.emoticon.setImageDrawable(emoticon)
    }

    private fun setBackground(typedArray: TypedArray) {
        val backgroundResId =
            typedArray.getResourceId(R.styleable.ThisMonthMongsil_bgg, R.drawable.push_allow_bar)
        binding.root.setBackgroundResource(backgroundResId)
    }

}
