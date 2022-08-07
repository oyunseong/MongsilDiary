package com.mongsil.mongsildiary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.printLog


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

        calendarViewModel.setDate(calendarViewModel.date.value ?: "999999")

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        calendarViewModel.date.observe(viewLifecycleOwner) {

            binding.date.text = Date().removeDayOfDate(it)
        }

        binding.calendar.setOnDateChangedListener { widget, date, selected ->
            ("widget:$widget, date:$date, selected:$selected").printLog("테스트")
        }

        binding.calendarTitle.setOnClickListener {
            "클릭 ".printLog()
            // TODO 값 date에 저장
            dayPickerDialog.show(parentFragmentManager, dayPickerDialog.tag)
        }
    }
}