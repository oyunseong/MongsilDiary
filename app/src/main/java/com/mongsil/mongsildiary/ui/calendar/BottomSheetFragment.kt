package com.mongsil.mongsildiary.ui.calendar

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.DialogDatepickerBinding

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val year: NumberPicker = view.findViewById(R.id.yearpicker_datepicker)
        val month: NumberPicker = view.findViewById(R.id.monthpicker_datepicker)
        val cancelBtn: TextView = view.findViewById(R.id.cancel_btn)
        val confirmBtn: TextView = view.findViewById(R.id.confirm_btb)

        //  순환 안되게 막기
        year.wrapSelectorWheel = false
        month.wrapSelectorWheel = false

        //  editText 설정 해제
        year.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        //  최소값 설정
        year.minValue = 2019
        month.minValue = 1

        //  최대값 설정
        year.maxValue = 2020
        month.maxValue = 12

        year.setOnValueChangedListener { _, _, newVal ->


        }



        //  취소 버튼 클릭 시
        confirmBtn.setOnClickListener {
            Toast.makeText(context, "취소 클릭", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        //  완료 버튼 클릭 시
//        save.setOnClickListener {
//            year_textview_statsfrag.text = (year.value).toString() + "년"
//            month_textview_statsfrag.text = (month.value).toString() + "월"
//
//            dialog.dismiss()
//            dialog.cancel()
//        }

    }
}