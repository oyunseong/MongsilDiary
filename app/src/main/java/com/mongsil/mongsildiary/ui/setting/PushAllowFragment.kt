package com.mongsil.mongsildiary.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        // TODO on,Off 상태에 따라 text 출력 수정 필요
        binding.morningSwitch.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                binding.offTv.visibility = View.GONE
                binding.onTv.visibility = View.VISIBLE
            } else {
                binding.onTv.visibility = View.GONE
                binding.offTv.visibility = View.VISIBLE
            }
        }
    }

}