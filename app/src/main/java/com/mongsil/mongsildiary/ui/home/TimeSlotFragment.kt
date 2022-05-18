package com.mongsil.mongsildiary.ui.home

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentTimeslotBinding
import com.mongsil.mongsildiary.model.TimeSlotViewPagerData
import com.mongsil.mongsildiary.utils.printLog
import com.mongsil.mongsildiary.utils.showToast
import kotlin.collections.ArrayList

class TimeSlotFragment : BaseFragment<FragmentTimeslotBinding>() {
    private val timeSlotViewPagerList: ArrayList<TimeSlotViewPagerData> = arrayListOf()
    private val timeSlotAdapter =
        TimeSlotAdapter(timeSlotViewPagerList, object :
            TimeSlotAdapter.ViewHolder.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                requireContext().showToast("${position}번째 item")
            }
        })

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTimeslotBinding {
        return FragmentTimeslotBinding.inflate(inflater, container, false)
        context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onButtonClickListener()

        binding.viewpager2.adapter = timeSlotAdapter
//        binding.springDotsIndicator.setViewPager2(binding.viewpager2)   // indicator connect adapter

        /**
         * 참고 블로그 :
         * https://black-jin0427.tistory.com/95
         * https://hyogeun-android.tistory.com/entry/ViewPager2%EC%97%90-%EA%B4%80%ED%95%9C-%EA%B3%A0%EC%B0%B0
         * */
        binding.viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.barIndicator.selectBar(position)
            }
        })

        binding.barIndicator.createIndicator(
            2,
            R.drawable.indicator_bar_off,
            R.drawable.indicator_bar_on,
            0
        )

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                "${binding.editText.length()}".printLog()
                // EditText에 아무것도 안 써있으면 우측 상단 등록 버튼 비활성화
                if (binding.editText.length() == 0) {
                    binding.toolbar.uploadBtn.isEnabled = false // 버튼 비활성화
                    binding.toolbar.uploadBtn.isClickable = false
                    binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#d5d9e2"))
                } else {
                    binding.toolbar.uploadBtn.isEnabled = true // 버튼 활성화
                    binding.toolbar.uploadBtn.isClickable = true
                    binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#7ea1ff"))
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun onButtonClickListener() {
        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.toolbar.uploadBtn.setOnClickListener {
            "클릭".printLog()
            if (binding.editText.length() == 0) {
                binding.toolbar.uploadBtn.isEnabled = false // 버튼 비활성화
                binding.toolbar.uploadBtn.isClickable = false
            } else {
                view?.findNavController()?.navigate(R.id.action_timeSlotFragment_to_homeFragment)
            }
        }
    }

    private fun addData() {
        for (i in 0..1) {
            timeSlotViewPagerList.add(TimeSlotViewPagerData("$i Page"))
        }
    }
}