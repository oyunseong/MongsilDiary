package com.mongsil.mongsildiary.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding

class CalendarFragment :BaseFragment<FragmentCalendarBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(inflater,container,false)
    }
}