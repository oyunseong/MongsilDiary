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

}