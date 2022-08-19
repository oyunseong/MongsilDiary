package com.mongsil.mongsildiary.ui.calendar

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mongsil.mongsildiary.MainViewModel
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding
import com.mongsil.mongsildiary.utils.Date
import com.prolificinteractive.materialcalendarview.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment : BaseFragment(), OnDateSelectedListener {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel by activityViewModels<MainViewModel>()
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

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        mainViewModel.date.observe(viewLifecycleOwner) {
            // TODO 캘린더를 눌러 이동한 날짜라면 Text 다르게 표시
            binding.date.text = Date().plusDotCalendarDay(it)
        }
        initCalendarView()
        binding.calendar.setOnDateChangedListener(this@CalendarFragment)

        calendarViewModel.slotData.observe(viewLifecycleOwner) {
            calendarViewModel.getData()
        }

        calendarViewModel.eventList.observe(viewLifecycleOwner) {
            binding.calendar.addDecorators(
                SaturdayDecorator(),
                SundayDecorator(),
                TodayDecorator(),
                EventDecorator(it)
            )
        }
    }

    private fun initCalendarView() {
        binding.calendar.apply {
            setOnDateChangedListener(this@CalendarFragment)
            addDecorators(
                SaturdayDecorator(),
                SundayDecorator(),
                TodayDecorator(),
                EventDecorator(calendarViewModel.eventList.value!!)
            )
        }

    }

    inner class SaturdayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val date: LocalDate = day.date
            val dayOfWeek: DayOfWeek = date.dayOfWeek
            return dayOfWeek.value == 6
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.calendar_weekend
                    )
                )
            )
        }
    }

    // 일요일
    inner class SundayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            val date = day.date
            val dayOfWeek = date.dayOfWeek
            return dayOfWeek.value == 7
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.calendar_weekend
                    )
                )
            )
        }
    }

    inner class EventDecorator(dates: ArrayList<CalendarDay>) :
        DayViewDecorator {
        private var dates = HashSet<CalendarDay>()
        private val drawable: Drawable

        init {
            this.dates = HashSet(dates)
            drawable = requireContext().resources.getDrawable(R.drawable.ic_emoticon_01)
        }

        override fun shouldDecorate(day: CalendarDay?): Boolean {
            return dates.contains(day)
        }

        override fun decorate(view: DayViewFacade) {
            view.setSelectionDrawable(drawable)
        }
    }

    class TodayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == CalendarDay.today()
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(StyleSpan(Typeface.BOLD))
            view.addSpan(RelativeSizeSpan(1.4f))
        }
    }

    /**
     * Calender Day Click event
     *
     * */
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        mainViewModel.setDate(date = date)
//        findNavController().popBackStack()
        // 화면을 옮긴 상태를 초기화 하기 위해서 화면을 다시 그림
        // 예를들어 slot을 오른쪽으로 넘기던가 화면을 내린 상태
        findNavController().popBackStack()
    }
}
