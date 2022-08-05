package com.mongsil.mongsildiary.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import java.text.Format
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : BaseFragment() {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
//    private val events = mutableMapOf<LocalDate, List<Event>>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title.setText(R.string.calendar)
        binding.toolbar.uploadBtn.visibility = View.GONE

        val dayPickerDialog = DayPickerDialog()

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }


        binding.titlea.setOnClickListener {
            "클릭 ".printLog()
            dayPickerDialog.show(parentFragmentManager, dayPickerDialog.tag)
        }

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        binding.calendar.apply {
            setup(currentMonth.minusMonths(10), currentMonth.plusMonths(10), daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (savedInstanceState == null) {
            binding.calendar.post {
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val binding = CalendarDayBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }
        binding.calendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.binding.exThreeDayText
                val dotView = container.binding.exThreeDotView

                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.btn_color)
//                            textView.setBackgroundResource(R.drawable.ic_emoticon_02)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.btn_color)
//                            textView.setBackgroundResource(R.drawable.ic_emoticon_01)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
//                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }
        }

    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendar.notifyDateChanged(it) }
            binding.calendar.notifyDateChanged(date)
//            updateAdapterForDate(date)
        }
    }
}