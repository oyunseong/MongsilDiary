package com.mongsil.mongsildiary.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mongsil.mongsildiary.databinding.DialogTimepickerBinding
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.showToast
import com.prolificinteractive.materialcalendarview.CalendarDay

class DayPickerDialog() : BottomSheetDialogFragment() {
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
        binding.yearPicker.wrapSelectorWheel = true
        binding.monthPicker.wrapSelectorWheel = true
        binding.dayPicker.wrapSelectorWheel = true

        binding.yearPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.monthPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.dayPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        //  최소값 설정
//        binding.yearPicker.displayedValues = arrayOf("AM", "PM")
        binding.yearPicker.minValue = 2021
        binding.monthPicker.minValue = 1
        binding.dayPicker.minValue = 1

        //  최대값 설정
        binding.yearPicker.maxValue = 2024
        binding.monthPicker.maxValue = 12
        binding.dayPicker.maxValue = 31

        binding.yearPicker.value = Date().currentDateTypeString()[0].toInt()
        binding.monthPicker.value = Date().currentDateTypeString()[1].toInt()
        binding.dayPicker.value = Date().currentDateTypeString()[2].toInt()

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.confirmBtb.setOnClickListener {
            try {
                val year = binding.yearPicker.value
                val month = underNumber(binding.monthPicker.value).toInt()
                val day = underNumber(binding.dayPicker.value).toInt()
                val result = CalendarDay.from(year, month, day)
                setFragmentResult(
                    "DayPickerDialogRequestKey",
                    bundleOf("DayPickerDialogBundleKey" to result)
                )
                requireContext().showToast(result.toString())
                dismiss()

            } catch (e: Exception) {
                e.printStackTrace()
                requireContext().showToast("알 수 없는 오류 발생!")
            }

        }
    }

    private fun underNumber(n: Int): String {
        return if (n < 10) {
            "0$n"
        } else {
            "$n"
        }
    }
}