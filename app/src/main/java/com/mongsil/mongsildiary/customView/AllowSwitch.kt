package com.mongsil.mongsildiary.customView

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.AllowSwitchBinding

class AllowSwitch : ConstraintLayout {

    private lateinit var binding: AllowSwitchBinding

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
        binding = AllowSwitchBinding.inflate(LayoutInflater.from(context), this, true)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        super.onDraw(canvas)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AllowSwitch)
        setTypedArray(typedArray)
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AllowSwitch, defStyle, 0)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        setTextColor(typedArray)
        setSwtichTextColor(typedArray)
        setBackground(typedArray)
//        setTextSize(typedArray)

        if (binding.settingSwitch.isChecked) {
            binding.offTv.visibility = View.GONE
        } else {
            binding.onTv.visibility = View.GONE
        }
        typedArray.recycle()
    }

    private fun setTextColor(typedArray: TypedArray) {
        val timeText = typedArray.getText(R.styleable.AllowSwitch_text)
        val time = typedArray.getText(R.styleable.AllowSwitch_time_text)
        binding.timeText.text = timeText
        binding.time.text = time
    }

    private fun setSwtichTextColor(typedArray: TypedArray) {
        val textColor = typedArray.getColor(R.styleable.AllowSwitch_switch_text_color, 0)
        binding.onTv.setTextColor(textColor)
        binding.offTv.setTextColor(textColor)
    }

    fun setOnCheckedChangeListener(key: String) {
        binding.settingSwitch.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                MyApplication.prefs.setState(key, isChecked)
                binding.offTv.visibility = View.GONE
                binding.onTv.visibility = View.VISIBLE
            } else {
                MyApplication.prefs.setState(key, isChecked)
                binding.onTv.visibility = View.GONE
                binding.offTv.visibility = View.VISIBLE
            }
        }
    }

    private fun setBackground(typedArray: TypedArray) {
        val backgroundResId =
            typedArray.getResourceId(R.styleable.AllowSwitch_bg, R.drawable.push_allow_bar)
        binding.root.setBackgroundResource(backgroundResId)
    }

    fun getPreferenceValue(key: String) {
        binding.settingSwitch.isChecked = MyApplication.prefs.getState(key, false)
    }
}