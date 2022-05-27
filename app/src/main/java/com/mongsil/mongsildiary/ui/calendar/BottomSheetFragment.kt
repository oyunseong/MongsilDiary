package com.mongsil.mongsildiary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.DialogDatepickerBinding

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: DialogDatepickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogDatepickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    // TODO 디폴트값 설정 질문
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  순환 안되게 막기
        binding.yearpickerDatepicker.wrapSelectorWheel = true
        binding.monthpickerDatepicker.wrapSelectorWheel = false

        //  editText 설정 해제
        binding.yearpickerDatepicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.monthpickerDatepicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        //  최소값 설정
        binding.yearpickerDatepicker.minValue = 2021
        binding.monthpickerDatepicker.minValue = 1


        //  최대값 설정
        binding.yearpickerDatepicker.maxValue = 2025
        binding.monthpickerDatepicker.maxValue = 12

        binding.yearpickerDatepicker.value = 2022

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.confirmBtb.setOnClickListener {
            dismiss()
        }
    }
}