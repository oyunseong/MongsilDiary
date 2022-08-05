package com.mongsil.mongsildiary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.CalendarDayBinding
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding
import com.mongsil.mongsildiary.databinding.FragmentCalendarHeaderBinding
import com.mongsil.mongsildiary.utils.*
import com.mongsil.mongsildiary.utils.setTextColorRes
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import java.text.Format
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val calendarViewModel by viewModels<CalendarViewModel>()
    private val dayPickerDialog = DayPickerDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title.setText(R.string.calendar)
        binding.toolbar.uploadBtn.visibility = View.GONE
        calendarViewModel.setDate(calendarViewModel.date.value ?: 999999)


        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        calendarViewModel.date.observe(viewLifecycleOwner) {
            binding.date.text = calendarViewModel.d(date = it)
        }


        binding.titlea.setOnClickListener {
            "클릭 ".printLog()
            // TODO 값 date에 저장
            dayPickerDialog.show(parentFragmentManager, dayPickerDialog.tag)
        }
    }
}