package com.mongsil.mongsildiary.ui.setting

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
import com.mongsil.mongsildiary.databinding.DialogTimepickerBinding

class TimePickerDialog : BottomSheetDialogFragment() {
    private var _binding: DialogTimepickerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTimepickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //  순환 안되게 막기
        binding.timePicker.wrapSelectorWheel = true
        binding.hourPicker.wrapSelectorWheel = true
        binding.minutePicker.wrapSelectorWheel = true

        binding.timePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.hourPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.minutePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        //  최소값 설정
        binding.timePicker.displayedValues = arrayOf("AM", "PM")
        binding.hourPicker.minValue = 1
        binding.minutePicker.minValue = 0

        //  최대값 설정
        binding.hourPicker.maxValue = 12
        binding.minutePicker.maxValue = 59

        binding.hourPicker.value = 7
        binding.minutePicker.maxValue = 0

        binding.cancelBtn.setOnClickListener {
            // TODO 기능
            dismiss()
        }

        binding.confirmBtb.setOnClickListener {
            dismiss()
        }
    }
}