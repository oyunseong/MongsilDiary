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
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.printLog
import com.prolificinteractive.materialcalendarview.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

import java.util.*

class CalendarFragment : BaseFragment(), OnDateSelectedListener {
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
        observeData()
        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        calendarViewModel.date.observe(viewLifecycleOwner) {
            binding.date.text = Date().removeDayOfDate(it)
        }

        initCalendarView()
    }

    private fun observeData() {
        calendarViewModel.date.observe(viewLifecycleOwner) {
            binding.date.text = Date().removeDayOfDate(it)
        }
    }

    private fun initCalendarView() {
        val arrayList = ArrayList<CalendarDay>()
        arrayList.add(CalendarDay.today())
        arrayList.add(CalendarDay.from(2022, 8, 13))
        arrayList.add(CalendarDay.from(2022, 6, 13))

        binding.calendar.apply {
            setOnDateChangedListener(this@CalendarFragment)
            addDecorators(
                SaturdayDecorator(),
                SundayDecorator(),
                TodayDecorator(),
                EventDecorator(arrayList)
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

    inner class EventDecorator(dates: Collection<CalendarDay>) :
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

    private var currentTime: Long = 0
    private var onClickCheck: Boolean = false
    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        val bundle = bundleOf("calendarDayKey" to date)
        findNavController().navigate(R.id.action_calendarFragment_to_homeFragment, bundle)
//        findNavController().popBackStack()
//        if (!onClickCheck) {
//            onClickCheck = true
//            currentTime = Calendar.getInstance().timeInMillis
//            "한번 클릭".printLog()
//            startTimer()
//        }
//        date.toString().printLog("onDateSelected date : ")
    }

    fun startTimer() {

    }

}
