package com.mongsil.mongsildiary.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mongsil.mongsildiary.MyApplication
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.databinding.FragmentPushAllowBinding

class PushAllowFragment : BaseFragment() {
    private var _binding: FragmentPushAllowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPushAllowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.uploadBtn.visibility = View.GONE

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.toolbar.title.text = "푸쉬 동의"

        binding.morning.setOnCheckedChangeListener("morning")
        binding.afternoon.setOnCheckedChangeListener("afternoon")
        binding.evening.setOnCheckedChangeListener("evening")
        binding.endOfTheDay.setOnCheckedChangeListener("end_of_the_day")

        binding.morning.getPreferenceValue("morning")
        binding.afternoon.getPreferenceValue("afternoon")
        binding.evening.getPreferenceValue("evening")
        binding.endOfTheDay.getPreferenceValue("end_of_the_day")
    }
}
