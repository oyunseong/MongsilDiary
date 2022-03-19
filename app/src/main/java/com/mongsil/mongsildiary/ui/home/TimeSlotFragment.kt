package com.mongsil.mongsildiary.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mongsil.mongsildiary.MainAdapter
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentWritingTimeslotBinding

class TimeSlotFragment : BaseFragment<FragmentWritingTimeslotBinding>() {
    private val dataSet: ArrayList<List<String>> = arrayListOf()
    private val mainAdapter = MainAdapter(dataSet, object : MainAdapter.OnItemClickListener{
        override fun onClick(v: View, position: Int) {
            showToast("${position}번째 item")
        }
    })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWritingTimeslotBinding {
        return FragmentWritingTimeslotBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewpager2.adapter = mainAdapter
    }
}