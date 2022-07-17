package com.mongsil.mongsildiary.ui.home.today

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentTodayBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.utils.converterTimeSlot

class TodayFragment : BaseFragment() {
    private lateinit var slot: Slot

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private val todayViewModel by viewModels<TodayViewModel>()
    private var emoticonSize: Int = 2

    companion object {
        const val PAGE_EMOTICONS_SIZE = 12
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onButtonClickListener()
        observeData()
        slot = arguments?.getParcelable<Slot>("slot") ?: Slot.mockSlot
        todayViewModel.setSlot(slot)
        binding.todayText.text = "오늘 ${slot.timeSlot.converterTimeSlot()}\n기분은 어떠세요?"
        binding.editText.setText(slot.text)
        emptyCheck()

        binding.viewpager2.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.barIndicator.selectBar(position)
                }
            })

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    if (position != 0) {
                        binding.advertisingBtn.visibility = View.VISIBLE
                        binding.advertisingImage.visibility = View.VISIBLE
                        binding.advertisingTv.visibility = View.VISIBLE
                        binding.advertising2Tv.visibility = View.VISIBLE
                        binding.advertisingBtn.bringToFront()
                        binding.advertisingImage.bringToFront()
                        binding.advertisingTv.bringToFront()
                        binding.advertising2Tv.bringToFront()
                    }
                }
            })
            clipToPadding = false
        }

        // TODO 광고버튼 수정
        binding.advertisingBtn.setOnClickListener {
            binding.advertisingBtn.visibility = View.GONE
            binding.advertisingImage.visibility = View.GONE
            binding.advertisingTv.visibility = View.GONE
            binding.advertising2Tv.visibility = View.GONE
        }

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emptyCheck()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    fun emptyCheck() {
        binding.textSizeTv.text = "${binding.editText.text.length}/30"
        if (binding.editText.text.toString() == "") {
            binding.toolbar.uploadBtn.isEnabled = false
            binding.toolbar.uploadBtn.isClickable = false
            binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#d5d9e2"))
        } else {
            binding.toolbar.uploadBtn.isEnabled = true
            binding.toolbar.uploadBtn.isClickable = true
            binding.toolbar.uploadBtn.setTextColor(Color.parseColor("#7ea1ff"))
        }
    }

    private fun observeData() {
        todayViewModel.emoticonState.observe(viewLifecycleOwner) { emoticons ->

            val chuckedEmoticons = emoticons.chunked(PAGE_EMOTICONS_SIZE)
            emoticonSize = if (emoticons.size % PAGE_EMOTICONS_SIZE == 0) {
                (emoticons.size / PAGE_EMOTICONS_SIZE)
            } else {
                (emoticons.size / PAGE_EMOTICONS_SIZE) + 1
            }

            binding.barIndicator.createIndicator(
                emoticonSize,
                R.drawable.indicator_bar_off,
                R.drawable.indicator_bar_on,
                0
            )

            binding.viewpager2.adapter =
                TodayViewPagerAdapter(
                    emoticonChunkList = chuckedEmoticons,
                    todayViewModel
                )
        }
    }

    private fun onButtonClickListener() {
        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.toolbar.uploadBtn.setOnClickListener {
            todayViewModel.insert(slot.copy(text = binding.editText.text.toString()))
            view?.findNavController()?.navigate(R.id.action_timeSlotFragment_to_homeFragment)
        }
    }
}