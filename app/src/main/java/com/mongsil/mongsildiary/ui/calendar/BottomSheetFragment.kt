package com.mongsil.mongsildiary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mongsil.mongsildiary.R

//TODO 뷰바인딩 적용하기
class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_datepicker,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val year: NumberPicker = view.findViewById(R.id.yearpicker_datepicker)
        val month: NumberPicker = view.findViewById(R.id.monthpicker_datepicker)
        val cancel: TextView = view.findViewById(R.id.cancel_btn)
        val save: TextView = view.findViewById(R.id.confirm_btb)
//        val year_tv: TextView = view.findViewById(R.id.year_tv)
//        val month_tv: TextView = view.findViewById(R.id.month_tv)

        //  순환 안되게 막기
        year.wrapSelectorWheel = false
        month.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

//        year.setOnValueChangedListener { numberPicker, i, i2 ->
//            year_tv.text = i2.toString()+"년"
//        }
//        month.setOnValueChangedListener { picker, oldVal, newVal ->
//            month_tv.text = newVal.toString()
//        }


        //  최소값 설정
        year.minValue = 2019
        month.minValue = 1

        //  최대값 설정
        year.maxValue = 2030
        month.maxValue = 12

        //  취소 버튼 클릭 시
        cancel.setOnClickListener {
            dismiss()
            Toast.makeText(requireContext(),"${year.value}",Toast.LENGTH_SHORT).show()
        }

        //  완료 버튼 클릭 시
        save.setOnClickListener {
//            year_tv.text = (year.value).toString() + "년"
//            month_tv.text = (month.value).toString() + "월"
            dismiss()

        }

    }
}