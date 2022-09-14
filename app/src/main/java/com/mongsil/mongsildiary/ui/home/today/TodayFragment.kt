package com.mongsil.mongsildiary.ui.home.today

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentTodayBinding
import com.mongsil.mongsildiary.domain.Slot
import com.mongsil.mongsildiary.ui.home.HomeFragment
import com.mongsil.mongsildiary.utils.converterTimeSlot
import com.mongsil.mongsildiary.utils.printLog
import com.mongsil.mongsildiary.utils.showToast


class TodayFragment : BaseFragment() {
    private lateinit var slot: Slot

    private var _binding: FragmentTodayBinding? = null
    private val binding get() = _binding!!

    private val todayViewModel by viewModels<TodayViewModel>()
    private var emoticonSize: Int = 2
    private lateinit var ft: FragmentTransaction
    private lateinit var backBtnCallback: OnBackPressedCallback

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

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        backBtnCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                binding.toolbar.backBtn.setOnClickListener() {
//                    activity?.onBackPressed() ?: "handleOnBackPressed activity is null".printLog()
//                }
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(
//            this,
//            backBtnCallback
//        )
//    }

//    override fun onDetach() {
//        super.onDetach()
//        backBtnCallback.remove()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onButtonClickListener()
        observeData()
        slot = arguments?.getParcelable<Slot>("slot") ?: Slot.mockSlot
        todayViewModel.setSlot(slot)
        todayViewModel.selectEmoticon(slot.emoticon)
        binding.todayText.text = "오늘 ${slot.timeSlot.converterTimeSlot()}\n기분은 어떠세요?"
        binding.editText.setText(slot.text)
        ft = requireActivity().supportFragmentManager.beginTransaction()
        emptyCheck()





        binding.viewpager2.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.barIndicator.selectBar(position)
                }
            })

//            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) {
//                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                    if (position != 0) {
//                        binding.advertisingBtn.visibility = View.VISIBLE
//                        binding.advertisingImage.visibility = View.VISIBLE
//                        binding.advertisingTv.visibility = View.VISIBLE
//                        binding.advertising2Tv.visibility = View.VISIBLE
//                        binding.advertisingBtn.bringToFront()
//                        binding.advertisingImage.bringToFront()
//                        binding.advertisingTv.bringToFront()
//                        binding.advertising2Tv.bringToFront()
//                    }
//                }
//            })
//            clipToPadding = false
        }

        // 이모티콘 리스트 스크롤 시 광고버튼
//        binding.advertisingBtn.setOnClickListener {
//            binding.advertisingBtn.visibility = View.GONE
//            binding.advertisingImage.visibility = View.GONE
//            binding.advertisingTv.visibility = View.GONE
//            binding.advertising2Tv.visibility = View.GONE
//        }

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
        binding.textSizeTv.text = "${binding.editText.text.length}"
        if (binding.editText.text.toString() == "") {
            binding.toolbar.uploadBtn.isEnabled = false
            binding.toolbar.uploadBtn.isClickable = false
            binding.toolbar.uploadBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.indicator_not_focus_color
                )
            )
        } else {
            binding.toolbar.uploadBtn.isEnabled = true
            binding.toolbar.uploadBtn.isClickable = true
            binding.toolbar.uploadBtn.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.indicator_focus_color
                )
            )
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
            findNavController().popBackStack()
        }

        binding.toolbar.uploadBtn.setOnClickListener {
            if (todayViewModel.selectedEmotionState.value == null) {
                requireContext().showToast("이모티콘을 선택해주세요")
            } else {
                todayViewModel.insert(
                    slot.copy(
                        emoticon = todayViewModel.selectedEmotionState.value ?: slot.emoticon,
                        text = binding.editText.text.toString()
                    )
                )
                findNavController().popBackStack()
            }
        }
    }
}