package com.mongsil.mongsildiary.ui.home.today

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.databinding.FragmentTimeslotBinding
import com.mongsil.mongsildiary.utils.printLog
import com.mongsil.mongsildiary.utils.showToast

class TimeSlotFragment : Fragment() {

    private var _binding: FragmentTimeslotBinding? = null
    private val binding get() = _binding!!

    //    private val emoticonViewModel by viewModels<EmoticonViewModel>()
    private val timeSlotViewModel by viewModels<TimeSlotViewModel>()
    private var emoticonSize: Int = 2

    companion object {
        const val PAGE_EMOTICONS_SIZE = 12
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeslotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onButtonClickListener()
        observeData()

        /**
         * 참고 블로그 :
         * https://black-jin0427.tistory.com/95
         * https://hyogeun-android.tistory.com/entry/ViewPager2%EC%97%90-%EA%B4%80%ED%95%9C-%EA%B3%A0%EC%B0%B0
         * */
        binding.viewpager2.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.barIndicator.selectBar(position)
                }
            })

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (position != 0){
                        // TODO 광고 버튼 추가
                        requireContext().showToast("$position 1번 페이지가 아님")
                    }
                }
            })

            clipToPadding = false
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                "${binding.editText.length()}".printLog()

                if (binding.editText.length() == 0) {
                    binding.toolbar.uploadBtn.isEnabled = false
                    binding.toolbar.uploadBtn.isClickable = false
                    binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#d5d9e2"))
                } else {
                    binding.toolbar.uploadBtn.isEnabled = true
                    binding.toolbar.uploadBtn.isClickable = true
                    binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#7ea1ff"))
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observeData() {
        timeSlotViewModel.emoticonState.observe(viewLifecycleOwner) { emoticons ->

            val chuckedEmoticons = emoticons.chunked(PAGE_EMOTICONS_SIZE)
            emoticonSize = emoticons.size / PAGE_EMOTICONS_SIZE + 1

            binding.barIndicator.createIndicator(
                emoticonSize,
                R.drawable.indicator_bar_off,
                R.drawable.indicator_bar_on,
                0
            )

            // viewpager의 onClickListener 구현 할 부분
            binding.viewpager2.adapter =
                TodayViewPagerAdapter(
                    emoticonChunkList = chuckedEmoticons,
                    onItemClickListener = {

                        "뷰페이저 클릭".printLog("TimeSlotAdapter")
                        requireContext().showToast("클릭")
                    }, //emoticonViewModel
                )
        }
    }

    private fun onButtonClickListener() {
        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.toolbar.uploadBtn.setOnClickListener {
            if (binding.editText.length() == 0) {
                binding.toolbar.uploadBtn.isEnabled = false // 버튼 비활성화
                binding.toolbar.uploadBtn.isClickable = false
            } else {
                view?.findNavController()?.navigate(R.id.action_timeSlotFragment_to_homeFragment)
            }
        }
    }
}