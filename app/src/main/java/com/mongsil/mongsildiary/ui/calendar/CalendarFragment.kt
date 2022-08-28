package com.mongsil.mongsildiary.ui.calendar

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mongsil.mongsildiary.MainViewModel
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding
import com.mongsil.mongsildiary.utils.Date
import com.mongsil.mongsildiary.utils.HorizontalItemDecorator
import com.mongsil.mongsildiary.utils.printLog
import com.prolificinteractive.materialcalendarview.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate


class CalendarFragment : BaseFragment(), OnDateSelectedListener {
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    val mainViewModel by activityViewModels<MainViewModel>()
    private val calendarViewModel by viewModels<CalendarViewModel>()
    private val dayPickerDialog = DayPickerDialog()
    private val thisMonthMongsilAdapter = ThisMonthMongsilAdapter()

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
        setThisMonthMongsilRecycler()

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        mainViewModel.date.observe(viewLifecycleOwner) {
            binding.date.text = Date().plusDotCalendarDay(it)
        }
        binding.calendar.setOnDateChangedListener(this@CalendarFragment)

        calendarViewModel.slotData.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.thisMonthMongsilEmptyText.visibility = View.VISIBLE
            } else {
                binding.thisMonthMongsilEmptyText.visibility = View.GONE
            }
            calendarViewModel.getSlotData()
            thisMonthMongsilAdapter.setMongsilList(it)
        }

        calendarViewModel.recordData.observe(viewLifecycleOwner) {
            calendarViewModel.getRecordData()
        }

        calendarViewModel.eventList.observe(viewLifecycleOwner) {
            it.forEach { _ ->
                binding.calendar.addDecorator(EventDecorator2(calendarViewModel.eventList.value))
            }
            binding.calendar.addDecorators(
                SaturdayDecorator(),
                SundayDecorator(),
                TodayDecorator(),
                EventDecorator(it),
            )
        }

        /**
         * Calendar 스크롤 시 해당 달 DB date 조회
         * Ex) SELECT * From SlotEntity date LIKE "202208__"
         * */
        binding.calendar.setOnMonthChangedListener { widget, date ->
            calendarViewModel.setSlotData(date)
        }
    }

    inner class TodayDecorator : DayViewDecorator {
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return day == CalendarDay.today()
        }

        override fun decorate(view: DayViewFacade) {
            view.addSpan(StyleSpan(Typeface.BOLD))
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
            view.setDaysDisabled(true)
        }
    }

    inner class EventDecorator(dates: HashMap<CalendarDay, Int>) :
        DayViewDecorator {
        private var dates = HashMap<CalendarDay, Int>()
        private val drawable: Drawable

        init {
            this.dates = HashMap(dates)
            drawable = requireContext().resources.getDrawable(R.drawable.ic_emoticon_01)
        }

        override fun shouldDecorate(day: CalendarDay?): Boolean {
//            return dates.contains(day)
            return dates.containsKey(day)
        }


        // TODO slot의 가장 앞의 Emoticon의 이모티콘을 표시 만약 없다면 default 이모티콘 표시
        override fun decorate(view: DayViewFacade) {
//            dates.forEach {
//                view.setSelectionDrawable(it.value)
//            }
//            view.setSelectionDrawable(requireContext().resources.getDrawable(R.drawable.ic_emoticon_02))
            view.setSelectionDrawable(drawable)
            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparency
                    )
                )
            )
        }
    }

    inner class EventDecorator2(dates: HashMap<CalendarDay, Int>?) :
        DayViewDecorator {
        private var dates = HashMap<CalendarDay, Int>()
        private val drawable: Drawable

        init {
            this.dates = HashMap(dates)
            drawable = requireContext().resources.getDrawable(R.drawable.ic_emoticon_01)
        }

        override fun shouldDecorate(day: CalendarDay?): Boolean {
//            return dates.contains(day)
            return dates.containsKey(day)
        }


        // TODO slot의 가장 앞의 Emoticon의 이모티콘을 표시 만약 없다면 default 이모티콘 표시
        override fun decorate(view: DayViewFacade) {
//            dates.forEach {
//                view.setSelectionDrawable(it.value)
//            }
//            view.setSelectionDrawable(requireContext().resources.getDrawable(R.drawable.ic_emoticon_02))
            view.setSelectionDrawable(drawable)
            view.addSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparency
                    )
                )
            )
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
        findNavController().popBackStack()
    }

    private fun setThisMonthMongsilRecycler() {
        binding.mongsilRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.mongsilRecycler.adapter = thisMonthMongsilAdapter
//        binding.mongsilRecycler.addItemDecoration(VerticalItemDecorator(10))
        binding.mongsilRecycler.addItemDecoration(HorizontalItemDecorator(20))
    }
}
