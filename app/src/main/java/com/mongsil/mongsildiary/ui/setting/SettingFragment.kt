package com.mongsil.mongsildiary.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.mongsil.mongsildiary.R
import com.mongsil.mongsildiary.base.BaseFragment
import com.mongsil.mongsildiary.base.ViewBindingFragment
import com.mongsil.mongsildiary.databinding.FragmentSettingBinding

class SettingFragment : ViewBindingFragment<FragmentSettingBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title.setText(R.string.setting)
        binding.toolbar.uploadBtn.visibility = View.GONE

        binding.pushBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_settingFragment_to_pushAllowFragment)
        }
        binding.backupBtn.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_to_settingFragment_to_backupFragment)
        }

        binding.toolbar.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}