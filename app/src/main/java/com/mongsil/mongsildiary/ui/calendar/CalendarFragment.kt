package com.mongsil.mongsildiary.ui.calendar

import android.app.AlertDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding

class CalendarFragment : BaseFragment<FragmentCalendarBinding>() {


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetDialog()

    }

    fun setBottomSheetDialog(){
        val bottomSheetView = layoutInflater.inflate(R.layout.dialog_datepicker,null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)

        binding.title.setOnClickListener {

            val year: NumberPicker = bottomSheetView.findViewById(R.id.yearpicker_datepicker)
            val month: NumberPicker = bottomSheetView.findViewById(R.id.monthpicker_datepicker)
            val cancel: TextView = bottomSheetView.findViewById(R.id.cancel_btn)
            val save: TextView = bottomSheetView.findViewById(R.id.confirm_btb)
            val year_tv: TextView = bottomSheetView.findViewById(R.id.year_tv)
            val month_tv: TextView = bottomSheetView.findViewById(R.id.month_tv)


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
            year.maxValue = 2030
            month.maxValue = 12

//            year.displayedValues= arrayOf("2021","2022","2023","2024","2025")

            //  취소 버튼 클릭 시
            cancel.setOnClickListener {
                bottomSheetDialog.dismiss()
                bottomSheetDialog.cancel()
                Toast.makeText(requireContext(),"${year.value}",Toast.LENGTH_SHORT).show()
            }

            //  완료 버튼 클릭 시
            save.setOnClickListener {
                year_tv.text = (year.value).toString() + "년"
                month_tv.text = (month.value).toString() + "월"

                bottomSheetDialog.dismiss()
                bottomSheetDialog.cancel()
            }

//
            bottomSheetDialog.show()
        }
    }

}

