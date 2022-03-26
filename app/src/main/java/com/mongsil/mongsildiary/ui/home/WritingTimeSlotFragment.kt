package com.mongsil.mongsildiary.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentWritingTimeslotBinding

class WritingSlotFragment : BaseFragment<FragmentWritingTimeslotBinding>(){
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWritingTimeslotBinding {
        return FragmentWritingTimeslotBinding.inflate(inflater,container,false)
    }
}