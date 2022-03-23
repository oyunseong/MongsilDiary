package com.mongsil.mongsildiary.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentCalendarBinding

class CalendarFragment :BaseFragment<FragmentCalendarBinding>(){
//    companion object{
//        val name: String = this::class.java.name
//    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCalendarBinding {
        return FragmentCalendarBinding.inflate(inflater,container,false)
    }


}